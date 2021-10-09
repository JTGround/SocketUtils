package server.events;

import connection.ClientConnection;

public class ConnectionListenerClientEvent {

    private ClientConnection clientConnection;


    public ConnectionListenerClientEvent(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public ClientConnection getClientConnection() {
        return this.clientConnection;
    }
}
