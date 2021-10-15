package server;

import connection.StreamClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.ConnectionListenerStateEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class StreamConnectionListener extends Thread {

    private ServerSocket serverSocket;
    private final int listeningPort;
    private ConnectionEventListener eventHandler;
    private ConnectionListenerStateEventListener listenerStateEventHandler;

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
                serverSocket = new ServerSocket(4444);
                serverSocket.bind(new InetSocketAddress(listeningPort));
                listenerStateEventHandler.listenerStarted(new ConnectionListenerStateEvent(serverSocket));
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
                listenerStateEventHandler.listenerClosed(new ConnectionListenerStateEvent(serverSocket));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public synchronized void interrupt() {
        super.interrupt();
        listenerStateEventHandler.listenerInterrupted(new ConnectionListenerStateEvent(serverSocket));
    }

    public synchronized void close() throws IOException {
        serverSocket.close();
        listenerStateEventHandler.listenerClosed(new ConnectionListenerStateEvent(serverSocket));
    }
}
