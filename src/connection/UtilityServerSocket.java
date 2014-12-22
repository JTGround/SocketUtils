package connection;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;

public class UtilityServerSocket extends ServerSocket {

	public UtilityServerSocket() throws IOException {
		super();
	}
	
	public UtilityServerSocket(int port) throws IOException {
		super(port);
	}

	public UtilitySocket acceptUtilitySocket() throws IOException {
		Socket socket = super.accept();
		UtilitySocket us = new UtilitySocket(socket);
		return us;
				
	}
	


}
