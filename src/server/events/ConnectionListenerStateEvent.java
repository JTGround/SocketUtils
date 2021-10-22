package server.events;

import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;

public class ConnectionListenerStateEvent {

    private ServerSocket serverSocket;


    public ConnectionListenerStateEvent(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

}
