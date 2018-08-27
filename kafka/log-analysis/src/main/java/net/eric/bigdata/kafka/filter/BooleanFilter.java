package net.eric.bigdata.kafka.filter;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

public class BooleanFilter extends BaseFilter {

	private static final long serialVersionUID = 1L;

	public boolean isKeep(TridentTuple tuple) {
		return tuple.getBoolean(0);
	}

}
