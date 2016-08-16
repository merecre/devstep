package lv.itsms.smsc_client;

public abstract class GatewayClient implements GatewayClientConnector, DataSender {

	public abstract String send(Object object);

	public abstract void connect();

	public abstract void disconnect();

}
