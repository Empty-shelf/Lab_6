package Work;

import Common.CommandShell;
import Common.Commander;
import Connection.*;

import java.io.IOException;
import java.net.*;

public class ClientMain {
    public static Contact contact;
    public static Receiver receiver;
    public static Sender sender;

    public static void main (String [] args) {

        contact = null;

        try {
            contact = new Contact(1234, "Localhost");
            System.out.println("Contact with server: " + contact.setContact());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        receiver = new Receiver(contact);
        sender = new Sender(contact);
        startWorking();
    }
    public static boolean check(){

        sender.send(new CommandShell());
        CommandShell command = null;
        try {
            command = receiver.receive();
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
        if(command != null){
            System.out.println(command.getName());
            return true;
        }
        return false;
    }

    public static void startWorking(){
        Commander term = new Commander();
        term.interactiveMod();
    }

       /* try {
            byte[] buf = {1, 2, 3, 4, 5};
            SocketAddress addr = new InetSocketAddress("localhost", 1234);
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packetOut = new DatagramPacket(buf, buf.length, addr);
            socket.send(packetOut);
            System.out.println("Sent");
            DatagramPacket packetIn = new DatagramPacket(buf, buf.length);
            socket.receive(packetIn);
            System.out.println("Received");
            for (byte i : buf) {
                System.out.println(i);
            }
        }catch (SocketException e){
            System.out.println("Can't create socket");
        }catch (IOException e){
            System.out.println("input/output error");
        }
        */

}
