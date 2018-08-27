package net.eric.bigdata.storm.upperword;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

@SuppressWarnings("serial")
public class UpperBolt extends BaseRichBolt {
	//声明一个输出收集器
	private OutputCollector collector;

	@SuppressWarnings("rawtypes") 
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// 初始化输出收集器collector，该组件实例化时执行一次，是由storm框架传过来的
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		// 主要处理逻辑，input为上一组件传入的元组tuple，此处对此元组进行处理，处理完再发送给下一组件
		//String cellphone = input.getStringByField("lower"); 根据元组字段名获得，或脚标获得(下面方法)都可以
		//String cellphone = input.getString(0);
		String cellphone = input.getStringByField("lower");
		
		//将cellphone转换为大写格式
		String cellphone_upper = cellphone.toUpperCase();
		
		//将转换完成的手机类型发送给下一个组件
		this.collector.emit(new Values(cellphone_upper));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//声明发送的字段名
		declarer.declare(new Fields("upper"));
	}

}
