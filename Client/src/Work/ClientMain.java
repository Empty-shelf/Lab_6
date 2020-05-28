package Work;

import Connection.*;

import java.net.SocketException;

public class ClientMain {

    public static Connection connection;
    public static Manager manager;

    public static void main (String [] args) {
        try {
            //установление связи с сервером
            connection = new Connection(1237, "Localhost");
            manager = Manager.getInstance(new Receiver(connection), new Sender(connection));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (true) {
            //System.out.println("Contact with server: " + connection.getConnection());
            manager.interactiveMod();
        }
    }
}

