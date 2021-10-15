package util;

import server.events.ConnectionListenerClientEvent;
import util.events.InputStreamDataReceivedEvent;

public interface InputStreamEventListener {

    public void dataReceived(InputStreamDataReceivedEvent evt);

}
