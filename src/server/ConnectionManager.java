package server;

import java.io.IOException;
import java.util.UUID;

import connection.ClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.NioConnectionListenerStateEvent;
import util.ClientConnectionHashMap;

import javax.net.ssl.HandshakeCompletedEvent;

public class ConnectionManager implements NioConnectionEventListener {
    
    private ClientConnectionHashMap connections = new ClientConnectionHashMap();
	private final ConnectionManagerProperties connectionManagerProperties;
	private NioConnectionListener listener;

    public ConnectionManager(ConnectionManagerProperties connectionManagerProperties) {
		this.connectionManagerProperties = connectionManagerProperties;

		this.listener = new NioConnectionListener(connectionManagerProperties.getListenerPort(), this);
		//this.listener.setDaemon(true);
    }

	public synchronized void startListener() {
		this.listener.start();
	}

	public synchronized void interruptListener() {
		this.listener.interrupt();
	}
    
    public synchronized void disconnectClient(UUID id) throws IOException {
		ClientConnection connection = connections.get(id);
		if(connection != null) {
			connection.disconnect();
			connections.remove(id);
		}
    }
    
    public synchronized void disconnectAll() throws IOException {
    	var it = connections.values().iterator();
    	while(it.hasNext()) {
			ClientConnection connection = it.next();
			connection.disconnect();
    	}
    	connections.clear();
    }

	@Override
	public synchronized void connectionAccepted(ConnectionListenerClientEvent evt) {
			System.out.println("accepted: " + evt.getClientConnection().getRemoteSocketAddress());
	}

	@Override
	public synchronized void connectionRemoved(ConnectionListenerClientEvent evt) {

	}

	@Override
	public synchronized void listenerStarted(NioConnectionListenerStateEvent evt) {
		System.out.println("listener started");
	}

	@Override
	public synchronized void listenerInterrupted(NioConnectionListenerStateEvent evt) {
			System.out.println("listener interrupted");
	}

	@Override
	public synchronized void listenerClosed(NioConnectionListenerStateEvent evt) {
		System.out.println("listener closed");
	}

	@Override
	public synchronized void handshakeCompleted(HandshakeCompletedEvent evt) {

	}
}
