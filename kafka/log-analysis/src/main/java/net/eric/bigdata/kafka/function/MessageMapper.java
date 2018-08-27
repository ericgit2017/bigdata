package net.eric.bigdata.kafka.function;

import java.io.Serializable;

import org.apache.storm.trident.tuple.TridentTuple;

public interface MessageMapper extends Serializable {
	public String toMessageBody(TridentTuple tuple);
}
