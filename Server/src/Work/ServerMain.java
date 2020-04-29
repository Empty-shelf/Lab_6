package Work;

import Common.Manager;
import Connection.*;

import java.io.IOException;

public class ServerMain {

    public static Contact contact;
    public static Sender sender;
    public static Receiver receiver;

    public static void main(String[] args) {
        Commander commander = new Commander(new Manager("file_user.csv"));
        setContact();
        //while (true) {
            commander.start(receiver.receive());
        //} //?????
    }

    public static boolean setContact() {
        contact = null;
        try {
            contact = new Contact(1234);
            contact.receiveConnection();
            sender = new Sender(contact);
            receiver = new Receiver(contact);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
