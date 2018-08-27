package net.eric.bigdata.kafka.function;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eric.bigdata.kafka.function.EWMA.Time;

public class MovingAverageFunction extends BaseFunction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MovingAverageFunction.class);
	
	
	private EWMA ewma;
	private Time emitRatePer;
	
//	public MovingAverageFunction(Time emitRatePer ) {
//		this.emitRatePer = emitRatePer;
//	}
	
	public MovingAverageFunction(EWMA ewma , Time emitRatePer ) {
		this.ewma = ewma;
		this.emitRatePer = emitRatePer;
	}
	public void execute(TridentTuple tuple, TridentCollector collector) {
		this.ewma.mark(tuple.getLong(0));
		LOG.debug("Rate:{}",this.ewma.getAverageRatePer(this.emitRatePer));
		collector.emit(new Values(this.ewma.getAverageRatePer(this.emitRatePer)));
	}
	
	
}
