package util;

import util.events.InputStreamDataReceivedEvent;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamListener extends Thread {

    private final InputStream stream;
    private final InputStreamEventListener eventListener;

    public InputStreamListener(InputStream stream, InputStreamEventListener eventListener) {
        this.stream = stream;
        this.eventListener = eventListener;
    }

    @Override
    public void run() {
        synchronized (eventListener) {

            byte[] buffer = new byte[1024];
            try {
                while (stream.read(buffer) != -1) {
                    eventListener.dataReceived(new InputStreamDataReceivedEvent());
                }
            } catch (IOException e) {

            }
        }

    }

}
