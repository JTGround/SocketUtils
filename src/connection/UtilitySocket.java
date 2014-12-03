/*
 * SocketConnection.java
 *
 * Created on March 9, 2006, 10:18 PM
 *
 */

package connection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import javax.net.ssl.SSLSocket;



//import org.apache.commons.logging.*;

public class UtilitySocket extends Socket implements Serializable {
    
    private static final long serialVersionUID = -7693049193453297145L;
    private long connectedAtMillis;
    private long disconnectedAtMillis;
    private UUID uniqueID;
    private UtilitySocket socket = null;
    private long bytesSent = 0;
    private long bytesReceived = 0;
    
    
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;
        
	public UtilitySocket(String host, int port) throws UnknownHostException, IOException {
    	super(host,port);
    	
    	uniqueID = UUID.randomUUID();    	
    	this.connectedAtMillis = System.currentTimeMillis();
    	this.disconnectedAtMillis = 0;
    	
    	initStreams();
    }
    
    private synchronized void initStreams() throws IOException {    	
        inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }
        
    public synchronized UUID getUniqueID() {
        return uniqueID;
    }
     
	public synchronized DataInputStream getInputStream() {
		return inputStream;
	}

	public synchronized DataOutputStream getOutputStream() {
		return outputStream;
	}
	
	public synchronized void write(byte[] data) throws IOException {
		outputStream.write(data);
		outputStream.flush();
		bytesSent += data.length;
    }  
	
	public synchronized void disconnect() throws IOException {
		inputStream.close();
		outputStream.close();
		socket.close();
		
		this.disconnectedAtMillis = System.currentTimeMillis();
		bytesSent = 0;
	}
		
	/*
    public synchronized boolean isSSLConnection() {
    	return this instanceof SSLSocket;
    }
    */
    public long getTimeConnectedMillis() {
        return connectedAtMillis;
    }
    
    public long getTimeDisconnectedMillis() {
    	return disconnectedAtMillis;
    }
    
    public Date getTimeConnected() {
    	return new Date(connectedAtMillis);
    }
    
    public Date getTimeDisconnected() {
    	return new Date(disconnectedAtMillis);
    }
        
    public long getElapsedTimeConnectedMillis() {
    	if(socket.isConnected()) {
    		long millisConnected = System.currentTimeMillis() - connectedAtMillis;
            return millisConnected;
    	} else {
    		long millisConnected = disconnectedAtMillis - connectedAtMillis;
    		return millisConnected;
    	}      
    }
	
    public long getBytesSent() {
		return bytesSent;
	}

	public long getBytesReceived() {
		return bytesReceived;
	}
    
    
}
