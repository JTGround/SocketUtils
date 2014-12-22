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
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.net.ssl.SSLSocket;



//import org.apache.commons.logging.*;

public class UtilitySocket implements Serializable {
	
    protected long bytesSent = 0;
    protected long bytesReceived = 0;
    
    private static final long serialVersionUID = -7693049193453297145L;
    private long connectedAtMillis;
    private long disconnectedAtMillis;
    private UUID uniqueID;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private Socket socket = null;
        
	public UtilitySocket(String host, int port) throws UnknownHostException, IOException {
    	
    	uniqueID = UUID.randomUUID();    	
    	this.connectedAtMillis = System.currentTimeMillis();
    	this.disconnectedAtMillis = 0;
    	socket = new Socket(host,port);
    	
    	initStreams();
    	this.listenData();
    }
	
	public UtilitySocket(Socket socket) throws IOException {
		uniqueID = UUID.randomUUID();    	
    	this.connectedAtMillis = System.currentTimeMillis();
    	this.disconnectedAtMillis = 0;
    	this.socket = socket;
    	
    	initStreams();
    	this.listenData();
	}
    
    private synchronized void initStreams() throws IOException {    	
        inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }
        
    public synchronized UUID getUniqueID() {
        return uniqueID;
    }
     
	public synchronized InputStream getInputStream() {
		return inputStream;
	}

	public synchronized OutputStream getOutputStream() {
		return outputStream;
	}
	
	public synchronized void write(byte[] buffer) throws IOException {
		outputStream.write(buffer);
		outputStream.flush();
		bytesSent += buffer.length;
    }  
	
	public synchronized void disconnect() throws IOException {
		inputStream.close();
		outputStream.close();
		socket.close();
		
		this.disconnectedAtMillis = System.currentTimeMillis();
		bytesSent = 0;
	}
	
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
	
	private void listenData() throws IOException {
		byte[] buffer = new byte[1024];
		while(true) {
			int size = inputStream.read(buffer);
			parseBuffer(Arrays.copyOf(buffer, size));
			bytesReceived += size;
			
		}
	}
	
	private void parseBuffer(byte[] buffer) throws UnsupportedEncodingException {
		System.out.println(new String(buffer,"UTF-8"));
	}
        
}
