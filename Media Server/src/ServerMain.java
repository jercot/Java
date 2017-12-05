import java.rmi.RemoteException;

import controller.MediaServer;
 
public class ServerMain {
	
	private static MediaServer server;
	
	public static void main(String[] args) {
		try {
			server = new MediaServer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		server.startServer();
	}
}