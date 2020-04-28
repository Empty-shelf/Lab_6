package Connection;

import Common.CommandShell;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Sender {
    private Contact contact;

    public Sender(Contact cont){
        contact = cont;
    }

    private byte bytes[] = new byte[32*1024];

    byte buf[];
    public boolean send(CommandShell command){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(command);
            buf = baos.toByteArray();
            baos.reset();
            return sendData(buf);

        }catch (IOException e) {
            System.out.println("Serialization error");
            return false;
        }
    }

    boolean sendData(byte[] b){
        DatagramSocket dataSock = contact.getSock();
        SocketAddress sockAddr = contact.getAddr();
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, sockAddr);
        try {
            dataSock.send(datagramPacket);
            System.out.println("Send");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}