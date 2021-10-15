package runner;

import connection.ClientConnection;
import connection.StreamClientConnection;
import server.ConnectionManager;
import server.ConnectionManagerProperties;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class SocketPairRunner {

    public static void main(String[] args) {
        var properties = new ConnectionManagerProperties();
        properties.setListenerPort(4444);
        properties.setMaxConnections(10);

        var connectionManager = new ConnectionManager(properties);
        connectionManager.startListener();

        StreamClientConnection client1 = null;
        try {
            client1 = new StreamClientConnection("127.0.0.1", 4444);
            var data = "jjj".getBytes();
            client1.write(data);
            data = "lll".getBytes();
            client1.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionManager.interruptListener();
    }
}
