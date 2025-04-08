package connection;

import util.events.InputStreamDataReceivedEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClientConnection extends ClientConnection {

    private SocketChannel socketChannel = null;

    public NioClientConnection(SocketChannel socket) throws IOException  {
        super(socket.socket());

        this.socketChannel = socket;
        //this.socket.configureBlocking(false);

        listenData();
    }

    public void write(ByteBuffer buffer) throws IOException {
        var written = socketChannel.write(buffer);
        var totalWritten = written;
        super.connectionStatistics.incrementBytesSent(written);

        while (written > 0 && buffer.hasRemaining()) {
            written = socketChannel.write(buffer);
            totalWritten += written;
            connectionStatistics.incrementBytesSent(written);
        }

        System.out.println("client " + uniqueID + ": writing " + totalWritten + " bytes");
    }

    public synchronized void disconnect() throws IOException {
        socketChannel.close();

        connectionStatistics.setDisconnected();
        System.out.println("client " + this.getUniqueID() + " sent: " + connectionStatistics.getBytesSent());
    }

    private synchronized void listenData() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(true) {
            buffer.clear();
            System.out.println("listen data");
            int read = socketChannel.read(buffer); // blocking
            System.out.println("listen data read");
            if (read < 0) {
                break;
            }
            buffer.flip();
            socketChannel.write(buffer); // blocking
            System.out.println("client " + uniqueID + ": reading " + read + " bytes");
            connectionStatistics.incrementBytesReceived(read);
        }
    }

    public boolean getIsConnected() {
        return socketChannel.isConnected();
    }

    @Override
    public void dataReceived(InputStreamDataReceivedEvent evt) {

    }
}
