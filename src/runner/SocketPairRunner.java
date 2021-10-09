package runner;

import connection.ClientConnection;
import server.ConnectionManager;
import server.ConnectionManagerProperties;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketPairRunner {

    public static void main(String[] args) {
        var properties = new ConnectionManagerProperties();
        properties.setListenerPort(4444);
        properties.setMaxConnections(10);

        var connectionManager = new ConnectionManager(properties);
        connectionManager.startListener();

        ClientConnection client1 = null;
        try {
            var socket1 = new Socket("127.0.0.1", 4444);
            client1 = new ClientConnection(socket1);
            var data = new byte[1024];
            client1.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionManager.interruptListener();
    }
}
