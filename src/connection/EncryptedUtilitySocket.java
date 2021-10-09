package connection;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class EncryptedUtilitySocket extends Socket {

	private static final long serialVersionUID = -3934727708328308375L;
	private InputStream inputStream = null;
    private OutputStream outputStream = null;

	public EncryptedUtilitySocket(String host, int port, Cipher cipher)throws UnknownHostException, IOException {
		super(host, port);
		
		initStreams(cipher);
	}
	
	private synchronized void initStreams(Cipher cipher) throws IOException {
    	inputStream = new CipherInputStream(super.getInputStream(), cipher);
    	outputStream = new CipherOutputStream(super.getOutputStream(), cipher);
    }

	@Override
	public synchronized InputStream getInputStream() {
		return this.inputStream;
	}

	@Override
	public synchronized OutputStream getOutputStream() {
		return this.outputStream;
	}
}
