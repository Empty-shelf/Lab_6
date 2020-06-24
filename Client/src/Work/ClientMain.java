package Work;

import Connection.*;

import java.net.SocketException;

public class ClientMain {

    public static Connection connection;
    public static Manager manager;

    public static void main (String [] args) {
        try {
            //установление связи с сервером
            connection = Connection.getInstance(2345, "Localhost");
            manager = Manager.getInstance(new Receiver(connection), new Sender(connection));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        manager.connect();
        while (true) {
            manager.interactiveMod();
        }
    }
}

