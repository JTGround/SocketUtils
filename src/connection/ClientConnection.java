package connection;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.UUID;

public class ClientConnection {

    private static final long serialVersionUID = -7693049193453297145L;

    private final UUID uniqueID = UUID.randomUUID();
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private Socket socket = null;
    private ConnectionStatistics connectionStatistics = new ConnectionStatistics();


    public ClientConnection(Socket socket) throws IOException  {
        this.socket = socket;

        initStreams();
        //listenData();
    }

    public SocketAddress getLocalSocketAddress() {
        return socket.getLocalSocketAddress();
    }

    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
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

        connectionStatistics.incrementBytesSent(buffer.length);
    }

    public synchronized void disconnect() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();

        connectionStatistics.setDisconnected();
    }

    public ConnectionStatistics getStatistics() {
        return connectionStatistics;
    }

    public boolean getIsConnected() {
        return socket.isConnected();
    }

    private void listenData() throws IOException {
        byte[] buffer = new byte[1024];
        while(true) {
            int size = inputStream.read(buffer);
            parseBuffer(Arrays.copyOf(buffer, size));

            connectionStatistics.incrementBytesReceived(size);
        }
    }

    private void parseBuffer(byte[] buffer) throws UnsupportedEncodingException {
        System.out.println(new String(buffer,"UTF-8"));
    }

    private synchronized void initStreams() throws IOException {
        inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }
}
