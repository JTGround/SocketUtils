package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.UUID;
import java.util.logging.Logger;

import connection.ClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.ConnectionListenerStateEvent;
import util.ClientConnectionHashMap;
import javax.net.ssl.HandshakeCompletedEvent;

public class ConnectionManager implements ConnectionEventListener, ConnectionListenerStateEventListener {
    
    private final ClientConnectionHashMap connections = new ClientConnectionHashMap();
	private final ConnectionManagerProperties connectionManagerProperties;
	//private NioConnectionListener nioConnectionListener;
	private final StreamConnectionListener streamConnectionListener;
	Logger logger = Logger.getLogger("SocketUtils");

    public ConnectionManager(ConnectionManagerProperties connectionManagerProperties) {
		this.connectionManagerProperties = connectionManagerProperties;

		//this.nioConnectionListener = new NioConnectionListener(connectionManagerProperties.getListenerPort(), this, this);
		this.streamConnectionListener = new StreamConnectionListener(connectionManagerProperties.getListenerPort(), this, this);
		//this.listener.setDaemon(true);
    }

	public synchronized void startListener() {
		//this.nioConnectionListener.start();
		this.streamConnectionListener.start();
	}

	public synchronized void interruptListener() {
		//this.nioConnectionListener.interrupt();
		this.streamConnectionListener.interrupt();
	}
    
    public synchronized void disconnectClient(UUID id) throws IOException {
		ClientConnection connection = connections.get(id);
		if(connection != null) {
			connection.disconnect();
			connections.remove(id);
		}
    }
    
    public synchronized void disconnectAll() throws IOException {
        for (ClientConnection connection : connections.values()) {
            connection.disconnect();
        }
    	connections.clear();
    }

	@Override
	public synchronized void connectionAccepted(ConnectionListenerClientEvent evt) {
		logger.info("connection accepted: " + evt.getClientConnection().getRemoteSocketAddress());
	}

	@Override
	public synchronized void connectionRemoved(ConnectionListenerClientEvent evt) {
		logger.info("connection removed: " + evt.getClientConnection().getRemoteSocketAddress());
	}

	@Override
	public synchronized void listenerStarted(ConnectionListenerStateEvent evt) {
		logger.info("listener started: listening on port " + evt.getPort());
	}

	@Override
	public synchronized void listenerInterrupted(ConnectionListenerStateEvent evt) {
		logger.info("listener interrupted ");
	}

	@Override
	public synchronized void listenerClosed(ConnectionListenerStateEvent evt) {
		logger.info("listener closed ");
	}

	@Override
	public synchronized void handshakeCompleted(HandshakeCompletedEvent evt) {

	}
}
