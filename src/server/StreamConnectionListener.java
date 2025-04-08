package server;

import connection.StreamClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.ConnectionListenerStateEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamConnectionListener extends Thread {

    private ServerSocket serverSocket;
    private final int listeningPort;
    private final ConnectionEventListener eventHandler;
    private final ConnectionListenerStateEventListener listenerStateEventHandler;
    private static final Logger logger = Logger.getLogger(StreamConnectionListener.class.getName());

    public StreamConnectionListener(int listeningPort, ConnectionEventListener eventHandler,
                                    ConnectionListenerStateEventListener listenerStateEventHandler) {
        this.listeningPort = listeningPort;
        this.eventHandler = eventHandler;
        this.listenerStateEventHandler = listenerStateEventHandler;
    }

    public int getListeningPort() {
        return listeningPort;
    }

    @Override
    public void run() {
        synchronized (eventHandler) {
            try {
                serverSocket = new ServerSocket(this.listeningPort);
                //serverSocket.bind(new InetSocketAddress(listeningPort));
                listenerStateEventHandler.listenerStarted(new ConnectionListenerStateEvent(this.listeningPort));
            } catch (IOException e) {
                logger.severe("error binding server socket (port=" + this.listeningPort + "): " + e.getMessage());
            }

            while (serverSocket.isBound()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    StreamClientConnection clientConnection = new StreamClientConnection(clientSocket);
                    eventHandler.connectionAccepted(new ConnectionListenerClientEvent(clientConnection));
                }
                catch (IOException e) {
                    logger.severe("error binding the server socket: " + e.getMessage());
                }
            }
            try {
                serverSocket.close();
                listenerStateEventHandler.listenerClosed(new ConnectionListenerStateEvent(this.listeningPort));
            }
            catch (IOException e) {
                logger.severe("error closing server socket: " + e.getMessage());
            }
        }

    }

    @Override
    public synchronized void interrupt() {
        super.interrupt();
        listenerStateEventHandler.listenerInterrupted(new ConnectionListenerStateEvent(this.listeningPort));
    }

    public synchronized void close() throws IOException {
        serverSocket.close();
        listenerStateEventHandler.listenerClosed(new ConnectionListenerStateEvent(this.listeningPort));
    }
}
