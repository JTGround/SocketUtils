package server;

import server.events.ConnectionListenerClientEvent;

import javax.net.ssl.HandshakeCompletedEvent;

public interface ConnectionEventListener {
	
	public void connectionAccepted(ConnectionListenerClientEvent evt);

    public void connectionRemoved(ConnectionListenerClientEvent evt);
    
    public void handshakeCompleted(HandshakeCompletedEvent evt);
}
