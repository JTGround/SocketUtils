package server;

import connection.ClientConnection;
import server.events.ConnectionListenerClientEvent;
import server.events.ConnectionListenerStateEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener extends Thread {

    private ServerSocket serverSocket;
    private final int listeningPort;
    private ConnectionEventListener eventHandler;
        
    public ConnectionListener(int listeningPort, ConnectionEventListener eventHandler) {
    	this.listeningPort = listeningPort;
    	this.eventHandler = eventHandler;
    }

    public int getListeningPort() {
        return listeningPort;
    }

    @Override
    public void run() {
    	try {
			serverSocket = new ServerSocket(listeningPort);
			eventHandler.listenerStarted(new ConnectionListenerStateEvent(serverSocket));
		} catch (IOException e) {
			e.printStackTrace();
		}
        while (serverSocket.isBound()) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(clientSocket);
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
        	eventHandler.listenerClosed(new ConnectionListenerStateEvent(serverSocket));
        }
        catch (IOException ex) {
                ex.printStackTrace();
        }
    }
    
    @Override
    public synchronized void interrupt() {
    	super.interrupt();
    	eventHandler.listenerInterrupted(new ConnectionListenerStateEvent(serverSocket));
    }
    
    public synchronized void close() throws IOException {
        serverSocket.close();
        eventHandler.listenerClosed(new ConnectionListenerStateEvent(serverSocket));
    }
}
