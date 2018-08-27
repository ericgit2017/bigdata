package net.eric.bigdata.kafka.function;

import java.util.Date;

import org.apache.storm.trident.tuple.TridentTuple;

public class NotifyMessageMapper implements MessageMapper {

	
	private static final long serialVersionUID = 1L;

	public String toMessageBody(TridentTuple tuple) {
		StringBuilder sb = new StringBuilder();
        sb.append("On " + new Date(tuple.getLongByField("timestamp")) + " ");
        
        sb.append("the application\"" + tuple.getStringByField("logger") + "\"");
        sb.append("changed alert statebased on a threshold of "
                +tuple.getDoubleByField("threshold") + ".\n");
        sb.append("The last value was" + tuple.getDoubleByField("average") + "\n");
        sb.append("The last message was \""+ tuple.getStringByField("message") + "\"");
        return sb.toString();
	}

}
