package server;

import connection.StreamClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.StreamConnectionListenerStateEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class StreamConnectionListener extends Thread {

    private ServerSocket serverSocket;
    private final int listeningPort;
    private StreamConnectionEventListener eventHandler;

    public StreamConnectionListener(int listeningPort, StreamConnectionEventListener eventHandler) {
        this.listeningPort = listeningPort;
        this.eventHandler = eventHandler;
    }

    public int getListeningPort() {
        return listeningPort;
    }

    @Override
    public void run() {
        synchronized (eventHandler) {
            try {
                serverSocket = new ServerSocket(4444);
                serverSocket.bind(new InetSocketAddress(listeningPort));
                eventHandler.listenerStarted(new StreamConnectionListenerStateEvent(serverSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (serverSocket.isBound()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    StreamClientConnection clientConnection = new StreamClientConnection(clientSocket);
                    eventHandler.connectionAccepted(new ConnectionListenerClientEvent(clientConnection));
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    break;
                }

                catch(Exception ex) {
                    ex.printStackTrace();
                }
                finally { }
            }
            try {
                serverSocket.close();
                eventHandler.listenerClosed(new StreamConnectionListenerStateEvent(serverSocket));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public synchronized void interrupt() {
        super.interrupt();
        eventHandler.listenerInterrupted(new StreamConnectionListenerStateEvent(serverSocket));
    }

    public synchronized void close() throws IOException {
        serverSocket.close();
        eventHandler.listenerClosed(new StreamConnectionListenerStateEvent(serverSocket));
    }
}
