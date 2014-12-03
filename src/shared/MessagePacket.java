package shared;

import java.util.UUID;

public class MessagePacket {
	
	private UUID clientID = null;
	private byte[] message = null;
	
	public MessagePacket(UUID connectionID, byte[] message) {
		this.clientID = connectionID;
		this.message = message;
	}
	
	public UUID getConnectionID() {
		return clientID;
	}
	
	public void setConnectionID(UUID value) {
		clientID = value;
	}
	
	public byte[] getMessage() {
		return message;
	}
	
	public void setMessage(byte[] value) {
		message = value;
	}
	
}
