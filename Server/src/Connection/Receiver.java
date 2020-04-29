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

    public CommandShell receive(){
        DatagramChannel channel = contact.getChannel();
        ByteBuffer bbf = ByteBuffer.wrap(bytes);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            bbf.clear();
            channel.read(bbf);
            ois = new ObjectInputStream(bais);
            CommandShell command = (CommandShell) ois.readObject();
            return command;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

}
