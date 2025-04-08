package connection;

import util.InputStreamEventListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public abstract class ClientConnection implements InputStreamEventListener {

    protected final UUID uniqueID = UUID.randomUUID();
    protected ConnectionStatistics connectionStatistics = new ConnectionStatistics();
    protected Socket socket = null;

    public ClientConnection(Socket socket) {
        this.socket = socket;
    }

    public ClientConnection(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public SocketAddress getLocalSocketAddress() {
        return socket.getLocalSocketAddress();
    }

    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    public ConnectionStatistics getStatistics() {
        return connectionStatistics;
    }

    public abstract void disconnect() throws IOException;

    public abstract boolean getIsConnected();

}
