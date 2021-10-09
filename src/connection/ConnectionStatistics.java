package connection;

public class ConnectionStatistics {


    private long bytesSent;
    private long bytesReceived;
    private long connectedAtMillis;
    private long disconnectedAtMillis;
    private boolean isConnected;

    public void setConnected() {

        this.connectedAtMillis = System.currentTimeMillis();
        this.disconnectedAtMillis = 0;
        isConnected = true;
    }

    public void setDisconnected() {
        this.disconnectedAtMillis = System.currentTimeMillis();
        isConnected = false;
    }

    public void incrementBytesSent(long bytesSent) {
        this.bytesSent += bytesSent;
    }

    public long getBytesSent() {
        return bytesSent;
    }

    public void incrementBytesReceived(long bytesReceived) {
        this.bytesReceived += bytesReceived;
    }

    public long getBytesReceived() {
        return bytesReceived;
    }

    public void reset() {
        this.bytesSent = 0;
        this.bytesReceived = 0;
    }

    public long getTimeConnectedMillis() {
        return connectedAtMillis;
    }

    public long getElapsedTimeConnectedMillis() {
        long millisConnected;
        if(isConnected) {
            millisConnected = System.currentTimeMillis() - connectedAtMillis;
        } else {
            millisConnected = disconnectedAtMillis - connectedAtMillis;
        }
        return millisConnected;
    }
}
