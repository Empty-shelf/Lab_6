package Work;

import Connection.*;
import UI.LoginWindow;
import UI.MainWindow;
import sun.rmi.runtime.Log;

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
        LoginWindow window = new LoginWindow();
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!LoginWindow.isLogged);
        manager.setLogin(LoginWindow.getLogin());
        MainWindow mainWindow = new MainWindow(LoginWindow.getLogin(), manager);
    }
}

