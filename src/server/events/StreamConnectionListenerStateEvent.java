package server.events;

import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;

public class StreamConnectionListenerStateEvent {

    private ServerSocket serverSocket;


    public StreamConnectionListenerStateEvent(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }
}
