package runner;

import connection.StreamClientConnection;
import server.ConnectionManager;
import server.ConnectionManagerProperties;
import server.StreamConnectionListener;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class SocketPairRunner {

    public static void main(String[] args) throws IOException {
        //StreamClientConnection client1 = new StreamClientConnection("localhost", 4444);


        Logger logger = Logger.getLogger("SocketUtils");
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new DefaultLogFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);

        var properties = new ConnectionManagerProperties();
        properties.setListenerPort(4444);
        properties.setMaxConnections(10);

        //var sk = new StreamClientSocket("127.0.0.1", 4444);


        var connectionManager = new ConnectionManager(properties);
        connectionManager.startListener();

        try {
            StreamClientConnection client1 = new StreamClientConnection("127.0.0.1", 4444);
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
