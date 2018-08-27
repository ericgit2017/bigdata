package net.eric.bigdata.storm.upperword;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

/*组织各组件形成一个完整的处理流程，即topology(类似于mapreduce中的job，但区别于topology的是
 * 它提交一次就结束，但topology提交后无休止运行)
 * 并将给topology提交到storm执行 
 */
public class UpperWordTopology {
	// 定义各组件名字
	private static final String RAND_WORDSPOUT_ID = "rand-wordspout";
	private static final String UPPER_BOLT_ID = "upper-bolt";
	private static final String PERSISTED_BOLT_ID = "persisted-bolt";
	private static final String TOPOLOGY_NAME = "upper-topology";
	
	public static void main(String[] args) throws Exception{
		UpperBolt upperBolt = new UpperBolt();
		PersistedBolt persistedBolt = new PersistedBolt();	
		RandomWordSpout randWordSpout = new RandomWordSpout();

		TopologyBuilder builder = new TopologyBuilder();
		
		//将spout组件定义到topology中，并发度2
		builder.setSpout(RAND_WORDSPOUT_ID, randWordSpout,2);
		
		//将转换大写bolt定义到topology中，并行度为2(即使用2个线程处理它)，并用.shuffleGrouping(RAND_WORDSPOUT_ID)指定它
		//接收RAND_WORDSPOUT_ID组件内容，使用随机分组策略接收，下面同理。
		builder.setBolt(UPPER_BOLT_ID,upperBolt,2).shuffleGrouping(RAND_WORDSPOUT_ID);
		
		//将持久化bolt定义到topology中，并指定它接收upper-bolt内容
		builder.setBolt(PERSISTED_BOLT_ID, persistedBolt, 2).shuffleGrouping(UPPER_BOLT_ID);
		
		//使用builder创建一个topology
		StormTopology topology = builder.createTopology();
		
		//配置一些topology在集群中运行的参数
		Config conf = new Config();
		//设置最大可以使用的worker数，根据storm集群环境可以自动负载均衡，例如：如果集群有两个supervisor节点，每个节点
		//有5个slots(端口/进程，supervisor.slots.ports控制)，NumWorkers设置为4，则每个supervisor上可能就有2个worker
		conf.setNumWorkers(4);  
		conf.setDebug(true);
		conf.setNumAckers(0);
		
		//将名称为upper-topology的topology提交给storm集群运行
		StormSubmitter.submitTopology(TOPOLOGY_NAME, conf, topology);
	}

}
