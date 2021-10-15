package server;

import server.events.ConnectionListenerClientEvent;
import server.events.NioConnectionListenerStateEvent;

import javax.net.ssl.HandshakeCompletedEvent;

public interface NioConnectionEventListener {
	
	public void connectionAccepted(ConnectionListenerClientEvent evt);

    public void connectionRemoved(ConnectionListenerClientEvent evt);
    
    public void listenerStarted(NioConnectionListenerStateEvent evt);
    
    public void listenerInterrupted(NioConnectionListenerStateEvent evt);
    
    public void listenerClosed(NioConnectionListenerStateEvent evt);
    
    public void handshakeCompleted(HandshakeCompletedEvent evt);
}
