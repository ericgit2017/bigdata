package net.eric.bigdata.kafka.topology;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.trident.OpaqueTridentKafkaSpout;
import org.apache.storm.kafka.trident.TridentKafkaConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.tuple.Fields;

import net.eric.bigdata.kafka.filter.BooleanFilter;
import net.eric.bigdata.kafka.function.EWMA;
import net.eric.bigdata.kafka.function.EWMA.Time;
import net.eric.bigdata.kafka.function.JsonProjectFunction;
import net.eric.bigdata.kafka.function.MovingAverageFunction;
import net.eric.bigdata.kafka.function.NotifyMessageMapper;
import net.eric.bigdata.kafka.function.ThresholdFilterFunction;
import net.eric.bigdata.kafka.function.XMPPFunction;

public class LogAnalysisTopology {
	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		//StaticHosts kafkaHosts = KafkaConfig.StaticHosts.fromHostString(Arrays.asList(new String[] {"localhost"}),1);
		BrokerHosts zk = new ZkHosts("storm:2181,storm1:2181,storm2:2181");
		TridentKafkaConfig spoutConf = new TridentKafkaConfig(zk, "mytopic");
		//TridentKafkaConfig spoutConf = new TridentKafkaConfig(kafkaHosts,"log-analysis");	
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		//spoutConf.forceStartOffsetTime(-1);
		OpaqueTridentKafkaSpout spout = new OpaqueTridentKafkaSpout(spoutConf);
		Stream spoutStream = topology.newStream("kafka-stream",spout);
		Fields jsonFields = new Fields("level","timestamp","message","logger");
		Stream parsedSteam = spoutStream.each(new Fields("str"), new JsonProjectFunction(jsonFields),jsonFields);
		parsedSteam = parsedSteam.project(jsonFields);	
		EWMA ewma =new EWMA().sliding(1.0,Time.MINUTES).withAlpha(EWMA.ONE_MINUTE_ALPHA);
		Stream AverageStream = parsedSteam.each(new Fields("timestamp"), new MovingAverageFunction(ewma,Time.MINUTES),new Fields("average"));
		ThresholdFilterFunction tff = new ThresholdFilterFunction(50D);
		Stream thresholdStream = AverageStream.each(new Fields("average"),tff, new Fields("change","threshold"));
		Stream filterStream = thresholdStream.each(new Fields("change"), new BooleanFilter());
		
		filterStream.each(filterStream.getOutputFields(), new XMPPFunction(new NotifyMessageMapper()),new Fields());
		return topology.build();
	}
	
	public static void main(String[] args) throws Exception{
		Config config = new Config();
		config.put(XMPPFunction.XMPP_USER, "eric@storm");
		config.put(XMPPFunction.XMPP_PASSWORD, "123456");
		config.put(XMPPFunction.XMPP_SERVER, "storm");
		config.put(XMPPFunction.XMPP_TO, "eric2@storm");
		config.setMaxSpoutPending(5);
		
		if(args.length ==0) {
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("log-analysis", config, buildTopology());
		}else {
			config.setNumWorkers(1);
			StormSubmitter.submitTopology(args[0], config, buildTopology());
		}
		}
		
}
