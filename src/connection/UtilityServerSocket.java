package connection;

import java.io.IOException;
import java.net.ServerSocket;

public class UtilityServerSocket extends ServerSocket {

	public UtilityServerSocket() throws IOException {
		super();
	}
	
	public UtilityServerSocket(int port) throws IOException {
		super(port);
	}

	@Override
	public UtilitySocket accept() throws IOException {
		return (UtilitySocket) super.accept();
	}
	


}
