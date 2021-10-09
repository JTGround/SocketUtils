package server.events;

import connection.ClientConnection;

import java.net.ServerSocket;


public class ConnectionListenerStateEvent {

	private ServerSocket serverSocket;


	public ConnectionListenerStateEvent(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}

}
