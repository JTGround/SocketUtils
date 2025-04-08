package server.events;

public class ConnectionListenerStateEvent {

    private final int port;

    public ConnectionListenerStateEvent(int port) {

        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
