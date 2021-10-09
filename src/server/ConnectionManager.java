package server;

import java.io.IOException;
import java.util.UUID;

import connection.ClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.ConnectionListenerStateEvent;
import util.ClientConnectionHashMap;

import javax.net.ssl.HandshakeCompletedEvent;

public class ConnectionManager implements ConnectionEventListener {
    
    private ClientConnectionHashMap connections = new ClientConnectionHashMap();
	private final ConnectionManagerProperties connectionManagerProperties;
	private ConnectionListener listener;

    public ConnectionManager(ConnectionManagerProperties connectionManagerProperties) {
		this.connectionManagerProperties = connectionManagerProperties;

		this.listener = new ConnectionListener(connectionManagerProperties.getListenerPort(), this);
		//this.listener.setDaemon(true);
    }

	public void startListener() {
		this.listener.start();
	}

	public void interruptListener() {
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
	public void connectionAccepted(ConnectionListenerClientEvent evt) {
		System.out.println("accepted: " + evt.getClientConnection().getRemoteSocketAddress());
	}

	@Override
	public void connectionRemoved(ConnectionListenerClientEvent evt) {

	}

	@Override
	public void listenerStarted(ConnectionListenerStateEvent evt) {
		System.out.println("listener started");
	}

	@Override
	public void listenerInterrupted(ConnectionListenerStateEvent evt) {

	}

	@Override
	public void listenerClosed(ConnectionListenerStateEvent evt) {

	}

	@Override
	public void handshakeCompleted(HandshakeCompletedEvent evt) {

	}
}
