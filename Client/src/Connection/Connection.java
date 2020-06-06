package Connection;

import java.io.IOException;
import java.net.*;

public class Connection {
    private static int PORT;
    private static String HOST;

    private SocketAddress addr;
    private DatagramSocket socket;

    //"объект-одиночка"
    public static Connection connection;

    SocketAddress getAddr() {
        return addr;
    }

    DatagramSocket getSocket() {
        return socket;
    }

    private Connection(int port, String host) throws SocketException {
        PORT = port;
        HOST = host;
        addr = new InetSocketAddress(host, port);
        socket = new DatagramSocket();
    }
    //инициализация/получение объекта одиночки
    public static Connection getInstance(int port, String host) throws SocketException {
        if (connection == null) {
            connection = new Connection(port, host);
            return connection;
        } else return connection;
    }

    //установление связи
    public boolean getConnection(){
        byte[] b = new byte[]{1, 2, 3};
        //на выход
        DatagramPacket packetOut = new DatagramPacket(b, b.length, addr);
        try {
            //отправка датаграммы
            socket.send(packetOut);
            //"обнуление" массива
            for (byte c : b) {
                c = 0;
            }
            //на вход
            DatagramPacket packetIn = new  DatagramPacket(b, b.length);
            socket.setSoTimeout(20000);
            socket.receive(packetIn);

            //соединение с адресом и портом (сервером)
            socket.connect(addr);
            return true;
        } catch (SocketTimeoutException e) {
            System.out.println("> Server isn't responding");
            return false;
        }catch (PortUnreachableException e){
            return false;
        } catch (IOException e) {
            System.out.println("> Smth wrong with connection");
            return false;
        }
    }
}



