package net.eric.bigdata.storm.upperword;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import net.eric.bigdata.storm.utils.Dormant;

@SuppressWarnings("serial")
public class RandomWordSpout extends BaseRichSpout {
	private SpoutOutputCollector collector;
	private int index = 0;
	private String[] cellphones = { "vivo", "huawei", "oppo", "samsung", "honor", "iphone", "meizu", "mi", "nokia",
			"moto", "sony", "zte", "meitu", "coolpad", "blackberry" };

	//初始化方法，在spout组件实例化时调用一次
	@SuppressWarnings("rawtypes")
	public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
		// 初始化采集器collector
		this.collector = collector;
	}

	//不断的往下一个组件发送
	public void nextTuple() {
		// 从cellphones数组中随机取出一个手机类型，也可以从kafka中拿数据
		Random rand = new Random();
		index = rand.nextInt(cellphones.length);
		String cellphone = cellphones[index];
		
		//将此商品类型封装为tuple，发送给下一个组件
		collector.emit(new Values(cellphone));
		
		//每发送一个休眠半秒钟
		Dormant.sleepForMillion(500);
	}

	//声明本组件发送的tuple的字段名
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("lower"));
	}

}
