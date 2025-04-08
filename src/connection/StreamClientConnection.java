package connection;

import util.InputStreamListener;
import util.events.InputStreamDataReceivedEvent;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class StreamClientConnection extends ClientConnection {

    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private InputStreamListener streamListener = null;
    Logger logger = Logger.getLogger("SocketUtils");

    public StreamClientConnection(Socket socket) throws IOException {
        super(socket);

        this.socket = socket;

        initStreams();

        this.streamListener = new InputStreamListener(inputStream, this);
        this.streamListener.start();
    }

    public StreamClientConnection(String host, int port) throws IOException {
        super(host, port);
        initStreams();

        this.streamListener = new InputStreamListener(inputStream, this);
        this.streamListener.start();
    }

    public void write(byte[] buffer) throws IOException {
        outputStream.write(buffer);
        outputStream.flush();

        connectionStatistics.incrementBytesSent(buffer.length);

        System.out.println("client " + uniqueID + ": writing " + buffer.length + " bytes");
    }

    public synchronized void disconnect() throws IOException {
        socket.close();

        connectionStatistics.setDisconnected();
        System.out.println("client " + this.getUniqueID() + " sent: " + connectionStatistics.getBytesSent());
    }

    public boolean getIsConnected() {
        return socket.isConnected();
    }

    @Override
    public void dataReceived(InputStreamDataReceivedEvent evt) {

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

    }

    private synchronized void initStreams() throws IOException {
        inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        System.out.println("client " + this.getUniqueID() + " : input/output streams initialized");
    }
}
