package server;

import java.io.Serializable;

public class ServerInstanceProperties implements Serializable {
    
	private static final long serialVersionUID = -1387080975465018173L;
    private int portNumber;
    private int maxNumberConnections;
    
    public ServerInstanceProperties(int portNumber, int maxNumberConnections) {
        this.portNumber = portNumber;
        this.maxNumberConnections = maxNumberConnections;
    }
    
    public int getPortNumber() {
        return portNumber;
    }
    
    public void setPortNumber(int port) {
        portNumber = port;
    }
    
    public int getMaxNumberConnections() {
        return maxNumberConnections;
    }
    
    public void setMaxNumberConnections(int num) {
        maxNumberConnections = num;
    }
    
    
}
