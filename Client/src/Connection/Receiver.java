package Connection;

import Common.CommandShell;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

public class Receiver {
    private Contact contact;

    public Receiver(Contact contact) {
        contact = contact;
    }

    public CommandShell receive() throws IOException {
        byte buf[] = new byte[32 * 1024];
        DatagramPacket packetIn = new DatagramPacket(buf, buf.length);
        try {
            contact.getSock().receive(packetIn);
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            ObjectInputStream ois = new ObjectInputStream(bais);
            CommandShell command = (CommandShell) ois.readObject();
            System.out.println("Received");
            return command;
        } catch (ClassNotFoundException e) {
            System.out.println("Bad command received");
            return null;
        }
    }
}
