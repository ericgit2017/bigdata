package net.eric.bigdata.storm.upperword;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class PersistedBolt extends BaseRichBolt {
	//声明fileWriter变量
	FileWriter fileWriter = null;
	
	@SuppressWarnings("rawtypes") 
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		try {
			fileWriter = new FileWriter("/root/tempdata/"+UUID.randomUUID());
		} catch (IOException e) {
			// 再往外抛出异常
			throw new RuntimeException();
		}
	}

	@Override
	public void execute(Tuple input) {
		//拿到上一组件处理后发送过来的手机类型
		//String upper_cellphone = input.getString(0);
		String upper_cellphone = input.getStringByField("upper");
				
		//再做一次简单处理，添加获得元组时间后缀
		String cellphone_suffix = upper_cellphone + "_" + System.currentTimeMillis();
		
		try {
			fileWriter.write(cellphone_suffix);
			fileWriter.write("\n");
			fileWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//此组件为最后一个节点，不需再声明字段
	}

}
