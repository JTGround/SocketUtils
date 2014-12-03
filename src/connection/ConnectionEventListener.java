package connection;

import javax.net.ssl.HandshakeCompletedEvent;

public interface ConnectionEventListener {
	
	public void connectionAccepted(ConnectionListenerEvent evt);

    public void connectionRemoved(ConnectionListenerEvent evt);
    
    public void listenerStarted(ConnectionListenerEvent evt);
    
    public void listenerInterrupted(ConnectionListenerEvent evt);
    
    public void listenerClosed(ConnectionListenerEvent evt);
    
    public void handshakeCompleted(HandshakeCompletedEvent evt);
}
