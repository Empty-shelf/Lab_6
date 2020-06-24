package Connection;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    private Receiver receiver;
    private Sender sender;

    public ConnectionManager(Receiver receiver, Sender sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    public Receiver getReceiver(){return this.receiver;}
    public Sender getSender(){return this.sender;}

}