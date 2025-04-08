package server;

import connection.NioClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.ConnectionListenerStateEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioConnectionListener extends Thread {

    private ServerSocketChannel serverSocket;
    private final int listeningPort;
    private ConnectionEventListener eventHandler;
    private ConnectionListenerStateEventListener listenerStateEventHandler;
        
    public NioConnectionListener(int listeningPort, ConnectionEventListener eventHandler,
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
                serverSocket = ServerSocketChannel.open();
                serverSocket.bind(new InetSocketAddress(listeningPort));
                listenerStateEventHandler.listenerStarted(new ConnectionListenerStateEvent(this.listeningPort));
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
                listenerStateEventHandler.listenerClosed(new ConnectionListenerStateEvent(this.listeningPort));
            }
            catch (IOException ex) {
                ex.printStackTrace();
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
