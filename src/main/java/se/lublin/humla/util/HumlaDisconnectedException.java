package se.lublin.humla.util;

/**
 * Called when a
 * Created by andrew on 01/03/17.
 */

@SuppressWarnings("serial")
public class HumlaDisconnectedException extends RuntimeException {
    public HumlaDisconnectedException() {
        super("Caller attempted to use the protocol while disconnected.");
    }

    public HumlaDisconnectedException(String reason) {
        super(reason);
    }
}
