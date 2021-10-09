package server;

import server.events.ConnectionListenerClientEvent;
import server.events.ConnectionListenerStateEvent;

import javax.net.ssl.HandshakeCompletedEvent;

public interface ConnectionEventListener {
	
	public void connectionAccepted(ConnectionListenerClientEvent evt);

    public void connectionRemoved(ConnectionListenerClientEvent evt);
    
    public void listenerStarted(ConnectionListenerStateEvent evt);
    
    public void listenerInterrupted(ConnectionListenerStateEvent evt);
    
    public void listenerClosed(ConnectionListenerStateEvent evt);
    
    public void handshakeCompleted(HandshakeCompletedEvent evt);
}
