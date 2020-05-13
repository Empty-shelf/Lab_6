package Connection;

import Common.CommandShell;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Manager {
    private DatagramSocket sock;
    private SocketAddress addr;

    public Manager(String hostname, int port){
        try {
            addr = new InetSocketAddress(hostname, port);
            sock = new DatagramSocket();
        }catch (IOException e){
            System.out.println("wrrrong");
        }
    }

    public void send(CommandShell command) {
        byte[] b = new byte[32 * 1024];
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(command);
            b = baos.toByteArray();
            DatagramPacket out = new DatagramPacket(b , b.length , addr);
            sock.send(out);
            System.out.println("> Sent a request");
        }catch (IOException e){
            System.out.println("Smth went wrong");
        }
    }

    public CommandShell receive() {
        byte b[] = new byte[32 * 1024];
        DatagramPacket in = new DatagramPacket(b, b.length);
        try {
            sock.receive(in);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(b));
            CommandShell com = (CommandShell) ois.readObject();
            System.out.println("> Received a response");
            System.out.println(com.getName());
            return com;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Smth went wrong");
            return null;
        }

    }
}
