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
        } catch (SocketException e) {
            e.printStackTrace();
        }

        receiver = new Receiver(contact);
        sender = new Sender(contact);

        //while (true) {
            try {
                startWorking();
                receiver.receive();
            }catch (IOException e){
                System.out.println("Input/output error");
            }
        //}
    }


    public static void startWorking(){
        Commander commander = new Commander();
        commander.interactiveMod();
    }
}
