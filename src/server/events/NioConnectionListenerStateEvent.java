package server.events;

import connection.ClientConnection;

import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;


public class NioConnectionListenerStateEvent {

	private ServerSocketChannel serverSocket;


	public NioConnectionListenerStateEvent(ServerSocketChannel serverSocket) {
		this.serverSocket = serverSocket;
	}

	public ServerSocketChannel getServerSocket() {
		return this.serverSocket;
	}

}
