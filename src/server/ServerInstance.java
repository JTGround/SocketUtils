package server;

public class ServerInstance {

    private final ServerInstanceProperties properties;
    private ConnectionManager connectionManager;

    public ServerInstance(ServerInstanceProperties properties) {
        this.properties = properties;

        //initialize connection manager
        var connectionManagerProperties = new ConnectionManagerProperties();
        connectionManagerProperties.setMaxConnections(properties.getMaxNumberConnections());
        connectionManagerProperties.setListenerPort(properties.getPortNumber());
        this.connectionManager = new ConnectionManager(connectionManagerProperties);
    }

    public void start() {
        connectionManager.startListener();
    }
}
