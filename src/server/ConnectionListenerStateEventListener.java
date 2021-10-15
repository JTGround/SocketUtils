package server;

import server.events.ConnectionListenerStateEvent;

public interface ConnectionListenerStateEventListener {


    public void listenerStarted(ConnectionListenerStateEvent evt);

    public void listenerInterrupted(ConnectionListenerStateEvent evt);

    public void listenerClosed(ConnectionListenerStateEvent evt);
}
