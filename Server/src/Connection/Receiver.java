package Connection;

import Common.CommandShell;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Receiver {

    private byte bytes[] = new byte[32*1024];

    private Contact contact;

    public Receiver(Contact contact){
        contact = contact;
    }

    public CommandShell receive() throws IOException {
        DatagramChannel datach = contact.getChannel();
        ByteBuffer bbf = ByteBuffer.wrap(bytes);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            bbf.clear();
            datach.read(bbf);
            ois = new ObjectInputStream(bais);
            CommandShell com = (CommandShell) ois.readObject();
            return com;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
