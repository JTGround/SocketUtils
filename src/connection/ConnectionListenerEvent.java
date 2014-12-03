package connection;

import java.net.ServerSocket;


public class ConnectionListenerEvent {
	
	private UtilitySocket clientConnection;
	private ServerSocket serverSocket;
	
	public ConnectionListenerEvent(UtilitySocket clientConnection) {
		this.clientConnection = clientConnection;
	}
	
	public ConnectionListenerEvent(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public UtilitySocket getClientConnection() {
		return clientConnection;
	}
}
