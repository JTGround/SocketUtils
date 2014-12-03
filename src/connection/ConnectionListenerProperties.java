package connection;

public class ConnectionListenerProperties {

	private int maxConnections;
	private int listenerPort;
	
	public ConnectionListenerProperties() {
		
	}
		
	public int getListenerPort() {
		return listenerPort;
	}

	public void setListenerPort(int listenerPort) {
		this.listenerPort = listenerPort;
	}

	public int getMaxConnections() {
		return maxConnections;
	}
	
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}
	
	
	
}
