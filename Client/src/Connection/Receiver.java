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
        byte bytes[] = new byte[32 * 1024];
        DatagramPacket packetIn = new DatagramPacket(bytes, bytes.length);
        try {
            contact.getSock().receive(packetIn);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            CommandShell com = (CommandShell) ois.readObject();
            System.out.println("Received");
            return com;
        } catch (ClassNotFoundException e) {
            System.out.println("Bad command received");
            return null;
        }
    }
}
