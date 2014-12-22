package util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import connection.UtilitySocket;

public class SocketCollection {

	private HashMap<UUID, Socket> connections = new HashMap<UUID, Socket>();
	private HashMap<UUID, UtilitySocket> utilitySockets = new HashMap<UUID, UtilitySocket>();
	
	public SocketCollection() {		
	}
	
	public void add(Socket connection) {
		connections.put(UUID.randomUUID(), connection);
	//	if(connection instanceof UtilitySocket) {
	//		UtilitySocket utilSocket = (UtilitySocket) connection;
	//		connections.put(utilSocket.getUniqueID(), utilSocket);
	//	} else {
	//		connections.put(UUID.randomUUID(), connection);
	//	}			
	}
	
	public void add(UtilitySocket connection) {
		utilitySockets.put(connection.getUniqueID(), connection);
	}
	
	public Socket get(UUID id) {
		return connections.get(id);
	}
	
	public void remove(UUID id) {
		connections.remove(id);
	}
	
	public void clear() throws SocketException {
		
		//check to see if there are any open sockets
		Iterator<Socket> it = connections.values().iterator();
		while(it.hasNext()) {
			Socket socket = it.next();			
			if(socket.isConnected()) {
				InetSocketAddress address = (InetSocketAddress) socket.getRemoteSocketAddress();
				throw new SocketException("Unable to remove a connected socket from the collection (remote host: " + address + ")");
			}
		}
		
		connections.clear();
	}
	
	public Collection<Socket> values() {
		return connections.values();
	}
	
	public Iterator<Socket> socketIterator() {
		return connections.values().iterator();
	}
	
	public Iterator<Entry<UUID, Socket>> entryIterator() {
		return connections.entrySet().iterator();
	}
	
	public int size() {
		return connections.size();
	}
	
	public boolean connectionExists(String host) {
		Iterator<Socket> it = connections.values().iterator();
		while(it.hasNext()) {
			Socket con = it.next();
			InetSocketAddress a = (InetSocketAddress) con.getRemoteSocketAddress();
			if(a.getHostName() == host) return true;
		}
		return false;
	}
	
	public boolean connectionExists(String host, int port) {
		Iterator<Socket> it = connections.values().iterator();
		while(it.hasNext()) {
			Socket con = it.next();
			InetSocketAddress a = (InetSocketAddress) con.getRemoteSocketAddress();
			if(a.getHostName() == host && a.getPort() == port) return true;
		}
		return false;
	}
	
	public InetSocketAddress[] getRemoteSocketAddresses() {
		ArrayList<InetSocketAddress> socketAddresses = new ArrayList<InetSocketAddress>();
		Iterator<Socket> iterator = connections.values().iterator();
		while(iterator.hasNext()) {
			Socket con = (Socket) iterator.next();
			InetSocketAddress a = (InetSocketAddress) con.getRemoteSocketAddress();
			socketAddresses.add(a);			
		}
		return (InetSocketAddress[]) socketAddresses.toArray();
	}
	
	public Set<InetAddress> getRemoteInetAddresses() {
		Set<InetAddress> addresses = new HashSet<InetAddress>();
		Iterator<Socket> iterator = connections.values().iterator();
		while(iterator.hasNext()) {
			Socket con = iterator.next();
			InetSocketAddress a = (InetSocketAddress) con.getRemoteSocketAddress();
			addresses.add(a.getAddress());
		}
		return addresses;
	}
}
