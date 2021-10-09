package util;

import connection.ClientConnection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ClientConnectionHashMap extends ConcurrentHashMap<UUID, ClientConnection> {

	public ClientConnectionHashMap() {
		super();
	}

	public boolean containsRemoteHost(String host) {
		Iterator<ClientConnection> it = this.values().iterator();
		while(it.hasNext()) {
			ClientConnection con = it.next();
			InetSocketAddress a = (InetSocketAddress) con.getRemoteSocketAddress();
			if(a.getHostName().toLowerCase(Locale.ROOT) == host.toLowerCase(Locale.ROOT)) return true;
		}
		return false;
	}
	
	public Set<InetAddress> getRemoteInetAddresses() {
		Set<InetAddress> addresses = new HashSet<InetAddress>();
		Iterator<ClientConnection> iterator = this.values().iterator();
		while(iterator.hasNext()) {
			ClientConnection con = iterator.next();
			InetSocketAddress a = (InetSocketAddress) con.getRemoteSocketAddress();
			addresses.add(a.getAddress());
		}
		return addresses;
	}
}
