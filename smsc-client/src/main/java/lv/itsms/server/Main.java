package lv.itsms.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.itsms.smsc_client.GatewayClient;
import lv.itsms.smsc_client.jsmpp.DBDeliveryStatusUpdater;
import lv.itsms.smsc_client.jsmpp.DeliveryReceiptMessageListener;
import lv.itsms.smsc_client.jsmpp.DeliveryStatusManager;
import lv.itsms.smsc_client.jsmpp.JSMPPGatewayClient;
import transfer.service.jpa.JPADAOfactory;
import lv.itsms.smsc_client.jsmpp.DeliveryStatusUpdater;

public class Main {

	final static String STOP_SERVER_MSG = "Type 'Stop' to stop server";
	final static String WAIT_STOP_MSG = "Please wait... thread is shutting down.";

	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		JPADAOfactory factoryDAO = new JPADAOfactory(); 
		
		DeliveryStatusUpdater statusUpdater = new DBDeliveryStatusUpdater(factoryDAO);
		DeliveryStatusManager deliveryManager = new DeliveryStatusManager(statusUpdater);
		
		GatewayClient gatewayClient = new JSMPPGatewayClient(new DeliveryReceiptMessageListener(deliveryManager));

		MessageController messageController = new MessageController(factoryDAO);
		MessageServer server = new MessageServer(messageController, deliveryManager, gatewayClient);		
		new Thread(server).start();

		manageServer(server);
	}

	private static void manageServer(MessageServer server) {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String command = null;

		while (true) {
			System.out.println(STOP_SERVER_MSG);
			try {
				command = bufferedReader.readLine();
			} catch (IOException e) {
				logger.error("Server exception: " + e.getMessage());
			}

			if (command.equals("Stop")) {
				server.kill();
				System.out.println(WAIT_STOP_MSG);
				break;
			} 
		}		
	}
}
