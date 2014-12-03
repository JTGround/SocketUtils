/*
 * ConnectionManager.java
 *
 * Created on June 18, 2006, 1:13 AM
 *
 */

package connection;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import javax.net.ssl.HandshakeCompletedEvent;

import proto.BaseProtocol;
import util.SocketCollection;

public class ConnectionManager {
    
    private SocketCollection connections = new SocketCollection();
    private boolean allowMultipleConnectionsToSameHost;

    public ConnectionManager() {
		this.allowMultipleConnectionsToSameHost = true;
    }
    
	public boolean getAllowMultipleConnectionsToSameHost() {
		return allowMultipleConnectionsToSameHost;
	}

	public void setAllowMultipleConnectionsToSameHost(boolean allowMultipleConnectionsToSameHost) {
		this.allowMultipleConnectionsToSameHost = allowMultipleConnectionsToSameHost;
	}
    
	public synchronized void addConnection(UtilitySocket socket) {
		connections.add(socket);
	}
	
    public synchronized void connectToHost(String host, int port) throws SocketException, UnknownHostException, IOException {
    	
    	//check if connection to host already exists
    	if(!allowMultipleConnectionsToSameHost) {
    		boolean exists = connections.connectionExists(host);
        	if(exists) throw new SocketException("A connection to this host already exists");
    	} else {
    		boolean exists = connections.connectionExists(host, port);
        	if(exists) throw new SocketException("A connection to this host and port already exists");
    	}    	
    	
		UtilitySocket connection = new UtilitySocket(host, port);
		connections.add(connection);    	
    }
    
    public synchronized void disconnectClient(UUID id) throws IOException {
        connections.remove(id);
    }
                
    public synchronized SocketCollection getConnections() {
        return connections;
    }
        
    public synchronized Socket getSocket(UUID id) {
    	return connections.get(id);
    }
    
    public synchronized void disconnectAll() throws IOException {
    	Iterator<Socket> it = connections.socketIterator();
    	while(it.hasNext()) {
    		Socket connection = it.next();
    		connection.close();
    	}
    	connections.clear();
    }
    
}
