package lv.itsms.server;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.itsms.smsc_client.GatewayClient;
import lv.itsms.smsc_client.jsmpp.DeliveryStatusManager;
import lv.itsms.smsc_client.jsmpp.MultipartMessageSender;
import transfer.domain.Sms;

/**
 * Sends messages to SMPP gateway 
 */

public class MessageServer implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(MessageServer.class);
	
	final static int PARALLELISM_LEVEL = 4;

	private static long timePause = 30000;

	private volatile boolean isRunning = true;

	MessageController messageController;

	DeliveryStatusManager deliveryManager;

	GatewayClient gatewayClient;	

	ForkJoinPool forkJoinPool = new ForkJoinPool(PARALLELISM_LEVEL);

	public MessageServer(MessageController messageController, DeliveryStatusManager deliveryManager, GatewayClient gatewayClient) {
		this.messageController = messageController; 
		this.deliveryManager = deliveryManager;
		this.gatewayClient = gatewayClient;
	}

	@Override
	public void run() {

		gatewayClient.connect();

		while (isRunning) {
			processSms();
			try {
				Thread.sleep(timePause);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}			
		}

		forkJoinPool.shutdown();
		gatewayClient.disconnect();
		messageController.finish();
	}

	private void processSms() {		
		if (!forkJoinPool.isQuiescent()) {
			System.out.println("Server is busy.");
			return;
		}

		//System.out.println("Server is running.");

		messageController.setupController();

		messageController.prepareSmsGroupSentMessages();
		messageController.updateSmsDeliveryStatus();
		messageController.updateSmsGroupStatusIfAllMessagesSent(); 

		List<Sms> smsContainer = messageController.selectSmsToBeSend();

		final Stream<Sms> stream = smsContainer
				.parallelStream();		

		Runnable task = () -> stream
				.forEach(s -> { 
					String hexMessageSMPPID;
					try {
						hexMessageSMPPID = gatewayClient.send(s);
						deliveryManager.updateSmsDeliveryStatus(s, hexMessageSMPPID);
					} catch (Exception e) {
						logger.error("Failed send message '" + s.getMSSID() + "'", e);
					}
				});

		forkJoinPool.execute(task);		

		if (forkJoinPool.isQuiescent())
			messageController.releaseResource();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void kill() {
		isRunning = false;
		forkJoinPool.shutdown();
	}
}
