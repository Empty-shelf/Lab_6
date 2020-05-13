package Connection;

import Common.CommandShell;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ConnectionManager {

    private DatagramChannel channel;
    private SocketAddress addr1;
    private SocketAddress addr2;
    private static int PORT;

    public ConnectionManager(int port){
        try {
            PORT = port;
            addr1 = new InetSocketAddress(PORT);
            channel = DatagramChannel.open().bind(addr1);
        }catch (IOException e){
            System.out.println("Smth went wrong");
        }
    }

    public CommandShell receive(){
        byte bytes[] = new byte[32*1024];
        ByteBuffer bbf = ByteBuffer.wrap(bytes);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        System.out.println("> Waiting for a request...");
        try {
            bbf.clear();

            addr2 = channel.receive(bbf);

            if (!channel.isConnected()) {
                channel.connect(addr2);
            }

            ois = new ObjectInputStream(bais);
            CommandShell command = (CommandShell) ois.readObject();
            return command;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Smth went wrong");
            return null;
        }
    }

    public void send(CommandShell command){
        byte buf[] = new byte[32*1024];
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        ObjectOutputStream ois = null;
        try {
            ois = new ObjectOutputStream(bais);
            ois.writeObject(command);
            buf = bais.toByteArray();
            ByteBuffer bbf = ByteBuffer.wrap(buf);
            channel.write(bbf);
            System.out.println("> Sent a response");
            if (command.getName().equals("exit")) channel.disconnect();
        } catch (IOException e) {
            System.out.println("Smth went wrong");
        }
    }
}