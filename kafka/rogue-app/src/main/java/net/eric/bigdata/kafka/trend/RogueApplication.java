package net.eric.bigdata.kafka.trend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RogueApplication {

	// Properties prop = new Properties();
	// prop.put("zk.connect", "storm:2181,storm1:2181,storm2:2181");
	// prop.put("metadata.broker.list", "storm:9092,storm1:9092,storm:9092");
	// prop.put("serializer.class", "kafka.serializer.StringEncoder");
	//
	// ProducerConfig config = new ProducerConfig(prop);
	// Producer<String, String> producer = new Producer<String, String>(config);
	//
	private static Logger LOG = LoggerFactory.getLogger(RogueApplication.class);

	public static void main(String[] args) throws Exception {
		int slowCount = 3;
		int fastCount = 6;
		for (int i = 0; i < slowCount; i++) {
			LOG.warn("This is a warning (slow state).");
			Thread.sleep(5000);
		}

		for (int i = 0; i < fastCount; i++) {
			LOG.warn("This is a warning (rapid state).");
			Thread.sleep(1000);
		}

		for (int i = 0; i < slowCount; i++) {
			LOG.warn("This is a warning (slow state).");
			Thread.sleep(5000);
		}

	}

}
