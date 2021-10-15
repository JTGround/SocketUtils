package server;

import connection.NioClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.NioConnectionListenerStateEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioConnectionListener extends Thread {

    private ServerSocketChannel serverSocket;
    private final int listeningPort;
    private NioConnectionEventListener eventHandler;
        
    public NioConnectionListener(int listeningPort, NioConnectionEventListener eventHandler) {
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
                serverSocket = ServerSocketChannel.open();
                serverSocket.bind(new InetSocketAddress(listeningPort));
                eventHandler.listenerStarted(new NioConnectionListenerStateEvent(serverSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (serverSocket.isOpen()) {
                try {
                    SocketChannel clientSocket = serverSocket.accept();
                    NioClientConnection clientConnection = new NioClientConnection(clientSocket);
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
                eventHandler.listenerClosed(new NioConnectionListenerStateEvent(serverSocket));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
    
    @Override
    public synchronized void interrupt() {
    	super.interrupt();
    	eventHandler.listenerInterrupted(new NioConnectionListenerStateEvent(serverSocket));
    }
    
    public synchronized void close() throws IOException {
        serverSocket.close();
        eventHandler.listenerClosed(new NioConnectionListenerStateEvent(serverSocket));
    }
}
