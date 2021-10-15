package server;

import java.io.IOException;
import java.util.UUID;

import connection.ClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.ConnectionListenerStateEvent;
import util.ClientConnectionHashMap;

import javax.net.ssl.HandshakeCompletedEvent;

public class ConnectionManager implements ConnectionEventListener, ConnectionListenerStateEventListener {
    
    private ClientConnectionHashMap connections = new ClientConnectionHashMap();
	private final ConnectionManagerProperties connectionManagerProperties;
	private NioConnectionListener nioConnectionListener;
	private StreamConnectionListener streamConnectionListener;

    public ConnectionManager(ConnectionManagerProperties connectionManagerProperties) {
		this.connectionManagerProperties = connectionManagerProperties;

		this.nioConnectionListener = new NioConnectionListener(connectionManagerProperties.getListenerPort(), this, this);
		this.streamConnectionListener = new StreamConnectionListener(connectionManagerProperties.getListenerPort(), this, this);
		//this.listener.setDaemon(true);
    }

	public synchronized void startListener() {
		this.nioConnectionListener.start();
	}

	public synchronized void interruptListener() {
		this.nioConnectionListener.interrupt();
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
	public synchronized void listenerStarted(ConnectionListenerStateEvent evt) {
		System.out.println("listener started");
	}

	@Override
	public synchronized void listenerInterrupted(ConnectionListenerStateEvent evt) {
			System.out.println("listener interrupted");
	}

	@Override
	public synchronized void listenerClosed(ConnectionListenerStateEvent evt) {
		System.out.println("listener closed");
	}

	@Override
	public synchronized void handshakeCompleted(HandshakeCompletedEvent evt) {

	}
}
