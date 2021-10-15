package server;

import server.events.ConnectionListenerClientEvent;
import server.events.NioConnectionListenerStateEvent;
import server.events.StreamConnectionListenerStateEvent;

import javax.net.ssl.HandshakeCompletedEvent;

public interface StreamConnectionEventListener {

    public void connectionAccepted(ConnectionListenerClientEvent evt);

    public void connectionRemoved(ConnectionListenerClientEvent evt);

    public void listenerStarted(StreamConnectionListenerStateEvent evt);

    public void listenerInterrupted(StreamConnectionListenerStateEvent evt);

    public void listenerClosed(StreamConnectionListenerStateEvent evt);

    public void handshakeCompleted(HandshakeCompletedEvent evt);
}
