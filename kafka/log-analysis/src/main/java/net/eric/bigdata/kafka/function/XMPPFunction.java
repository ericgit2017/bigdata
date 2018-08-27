package net.eric.bigdata.kafka.function;

import java.util.Map;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.TridentOperationContext;
import org.apache.storm.trident.tuple.TridentTuple;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMPPFunction extends BaseFunction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(XMPPFunction.class);
	
	public static final String XMPP_TO="storm.xmpp.to";
	public static final String XMPP_USER="storm.xmpp.user";
	public static final String XMPP_PASSWORD="storm.xmpp.password";
	public static final String XMPP_SERVER="storm.xmpp.server";
	
	private XMPPConnection xmppConnection;
	private String to;
	private MessageMapper mapper;
	
	public XMPPFunction(MessageMapper mapper) {
		this.mapper = mapper;
	}
	
	@SuppressWarnings("rawtypes") 
	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		LOG.debug("Prepare:{}",conf);
		super.prepare(conf, context);
		this.to = (String) conf.get(XMPP_TO);
		ConnectionConfiguration config = new ConnectionConfiguration((String)conf.get(XMPP_SERVER));
		this.xmppConnection = new XMPPConnection(config);
		try {
		this.xmppConnection.connect();
		this.xmppConnection.login((String)conf.get(XMPP_USER),(String)conf.get(XMPP_PASSWORD));
		}catch(XMPPException e) {
			LOG.warn("Error Initializing XMPP Channel",e);
		}
		
	}





	public void execute(TridentTuple tuple, TridentCollector collector) {
		Message msg = new Message(this.to,Type.normal);
		msg.setBody(this.mapper.toMessageBody(tuple));
		this.xmppConnection.sendPacket(msg);
		
	}

}
