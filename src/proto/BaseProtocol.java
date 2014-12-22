package proto;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import connection.UtilitySocket;


public class BaseProtocol {


    private enum ControlLock {
    	local,
    	remote,
    	none
    }
    private ControlLock currentControlLock = ControlLock.none;
	private HashMap<String, byte[]> commandMap = new HashMap<String, byte[]>();
	private byte[] lastRead;
	
	public BaseProtocol() {
		initCommands();
	}
	
	private void initCommands() {
		commandMap.put(	"requestControlLock", 	new byte[] {(byte) 0x00 });
		commandMap.put(	"acceptControlLock", 	new byte[] {(byte) 0x01 });
		commandMap.put(	"denyControlLock", 		new byte[] {(byte) 0x02 });
	}
	
	public synchronized boolean requestControlLock(UtilitySocket socket) {
		if(currentControlLock == ControlLock.remote) return false;
		
		try {
			send(socket, "requestControlLock");
			read(socket);
			if(lastRead != com("acceptControlLock")) return false;
		} 
		catch (IOException e) { return false; }
		
		currentControlLock = ControlLock.local;
		return true;
	}
	
	private void send(UtilitySocket socket, String command) throws IOException {
		socket.write(commandMap.get(command));
	}
	
	private void read(UtilitySocket socket) throws IOException {
		//socket.read(lastRead);
	}

	private byte[] com(String command) {
		return commandMap.get(command);
	}
}
