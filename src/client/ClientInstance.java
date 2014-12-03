package client;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.net.ssl.HandshakeCompletedEvent;

import proto.BaseProtocol;
import util.SocketCollection;
import connection.ConnectionListenerEvent;
import connection.ConnectionEventListener;
import connection.ConnectionListener;
import connection.ConnectionManager;
import connection.ConnectionListenerProperties;
import connection.UtilitySocket;

public class ClientInstance implements ConnectionEventListener {

	private ConnectionManager conman;
	private ConnectionListener listener;
	private String nickname;	
	private BaseProtocol baseProtocol = new BaseProtocol();

	public ClientInstance(ConnectionListenerProperties listenerProperties) throws IOException {
			initialize(listenerProperties);
			
			conman = new ConnectionManager();
	}
	
	private void initialize(ConnectionListenerProperties properties) throws IOException {
		
		//verify properties
    	if(properties.getListenerPort() <= 1024 || properties.getListenerPort() > 65535) 
    		properties.setListenerPort(4444);
    	if(properties.getMaxConnections() < 0)
    		properties.setMaxConnections(0);
    	
		listener = new ConnectionListener(properties.getListenerPort(), this);
    	listener.setDaemon(true);
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void startListener() {
		listener.start();
	}
	
	public void stopListener() {
		listener.interrupt();
	}
	
	public int getListeningPort() {
		return listener.getListeningPort();
	}
	
	public void connect(String host, int port) throws SocketException, UnknownHostException, IOException {
		conman.connectToHost(host, port);
	}
	
	public void disconnectClient(String identifier) throws IOException {
			conman.disconnectClient(UUID.fromString(identifier));
	}
	
	public void disconnectAllClients() throws IOException {
			conman.disconnectAll();
	}
	
	public SocketCollection getConnections() {
		return conman.getConnections();
	}

	@Override
	public synchronized void connectionAccepted(ConnectionListenerEvent evt) {
		System.out.println("[" + nickname + "] connection added: " + evt.getClientConnection().getUniqueID());
		UtilitySocket socket = evt.getClientConnection();
		conman.addConnection(socket);		

		baseProtocol.requestControlLock(socket);
	}

	@Override
	public synchronized void connectionRemoved(ConnectionListenerEvent evt) {
    	System.out.println("[" + nickname + "] connection removed: " + evt.getClientConnection().getUniqueID());
	}

	@Override
	public void listenerStarted(ConnectionListenerEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listenerInterrupted(ConnectionListenerEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listenerClosed(ConnectionListenerEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handshakeCompleted(HandshakeCompletedEvent evt) {
		// TODO Auto-generated method stub
		
	}

	
}
