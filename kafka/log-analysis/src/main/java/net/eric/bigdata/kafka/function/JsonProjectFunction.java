package net.eric.bigdata.kafka.function;

import java.util.Map;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.json.simple.JSONValue;

public class JsonProjectFunction extends BaseFunction {

	private static final long serialVersionUID = 1L;
	
	private Fields fields;
	public JsonProjectFunction(Fields fields) {
		this.fields = fields;
	}

	public void execute(TridentTuple tuple, TridentCollector collector) {
		String json = (String) tuple.getValue(0);
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String, Object>) JSONValue.parse(json);
		Values values = new Values();
		for(int i=0;i<this.fields.size();i++) {
			values.add(map.get(this.fields.get(i)));
			System.out.println("++++++++++++++++++++++++++++++++++++++");
		}
		collector.emit(values);
	}
	
	
}
