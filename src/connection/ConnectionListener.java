
package connection;

import java.io.IOException;


public class ConnectionListener extends Thread {

    private UtilityServerSocket serverSocket;
    private final int listeningPort;
    private ConnectionEventListener eventHandler;
        
    public ConnectionListener(int listeningPort, ConnectionEventListener eventHandler) {
    	this.listeningPort = listeningPort;
    	this.eventHandler = eventHandler;
    }
    
    public int getListeningPort() {
    	return listeningPort;
    }
    
    public void run() {
    	try {
			serverSocket = new UtilityServerSocket(listeningPort);
			eventHandler.listenerStarted(new ConnectionListenerEvent(serverSocket));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        while (serverSocket.isBound()) {
            try {
                UtilitySocket clientSocket = serverSocket.acceptUtilitySocket();
                eventHandler.connectionAccepted(new ConnectionListenerEvent(clientSocket));
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
        	eventHandler.listenerClosed(new ConnectionListenerEvent(serverSocket));
        }
        catch (IOException ex) {
                ex.printStackTrace();
        }
    }
    
    @Override
    public synchronized void interrupt() {
    	super.interrupt();
    	eventHandler.listenerInterrupted(new ConnectionListenerEvent(serverSocket));
    }
    
    public synchronized void close() throws IOException {
        serverSocket.close();
        eventHandler.listenerClosed(new ConnectionListenerEvent(serverSocket));
    }

                
}
