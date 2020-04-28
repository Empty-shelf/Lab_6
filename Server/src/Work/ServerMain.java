package Work;

import Common.Commander;
import Common.Manager;
import Connection.*;

import java.io.IOException;
import java.net.*;

public class ServerMain {

    public static Contact contact;
    public static Sender sender;
    public static Receiver receiver;

    public static void main(String[] args) {
        Manager manager = new Manager("Coll_Path");
       // terminal = new Terminal(args);
       // terminal.start();
       // askServerer();

    }
















    public static boolean setContact(){
        contact = null;
        try {
            contact = new Contact(4445);
            contact.receiveConnection();
            sender = new Sender(contact);
            receiver = new Receiver(contact);
//            Command com = receiver.receive();
//            System.out.println(com.getNameOfCommand());
//            sender.send(com);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void askServerer(){
        Scanner scan = new Scanner(System.in);
        String string;
        System.out.print("Work with client or server user? (Client/User/exit)\n> ");
        string = scan.nextLine();
        if (string.contains("Client")){
            ServerUI.setHaveClient(setContact());
            if(ServerUI.isHaveClient()){
                ServerUI.start();
            }else {
                System.out.println("Haven't client");
            }
        }else if(string.contains("User")){
            ExeClass eCla = new ExeClass();
            eCla.start();
        }else if (string.equals("exit")){
            new Save().execute(null,null,null);
            return;
        }else {
            System.out.println("Wrong entering");
        }
        askServerer();
    }











        /*try {
            byte[] buf = new byte[5];
            System.out.println("Waiting...");
            DatagramSocket socket = new DatagramSocket(1234);
            DatagramPacket packetIn = new DatagramPacket(buf, buf.length);
            socket.receive(packetIn);
            for (int i = 0; i < 5; i++) {
                buf[i] *= 2;
            }
            System.out.println("Received");
            System.out.println(packetIn);
            DatagramPacket packetOut = new DatagramPacket(buf, buf.length, packetIn.getAddress(), packetIn.getPort());
            socket.send(packetOut);
            System.out.println("Sent");
            socket.close();
        }catch (SocketException e){
            System.out.println("Can't create socket");
        }catch (IOException e){
            System.out.println("input/output error");
        }

         */
    }
