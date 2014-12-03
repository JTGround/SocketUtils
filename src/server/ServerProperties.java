/*
 * ServerInstanceDescriptor.java
 *
 * Created on June 6, 2007, 2:47 PM
 */

package server;

import java.io.Serializable;

public class ServerProperties implements Serializable {
    
	private static final long serialVersionUID = -1387080975465018173L;
	private String instanceName;
    private int portNumber;
    private int maxNumberConnections;
    
    public ServerProperties() {
    }
        
    public String getInstanceName() {
        return instanceName;
    }
    
    public void setInstanceName(String name) {
        instanceName = name;
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
