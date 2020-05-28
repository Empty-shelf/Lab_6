package Connection;

import java.io.IOException;
import java.net.*;

public class Connection {
    private static int PORT;
    private static String HOST;

    private SocketAddress addr;
    private DatagramSocket socket;

    SocketAddress getAddr() {
        return addr;
    }

    DatagramSocket getSocket() {
        return socket;
    }

    public Connection(int port, String host) throws SocketException {
        PORT = port;
        HOST = host;
        addr = new InetSocketAddress(host, port);
        socket = new DatagramSocket();
    }

    //установление связи
    public boolean getConnection() {
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
           // if (!receive(new DatagramPacket(b, b.length))) return false;
            DatagramPacket packetIn = new  DatagramPacket(b, b.length);
            socket.setSoTimeout(20000);
            socket.receive(packetIn);

            //соединение с адресом и портом (сервером)
            socket.connect(addr);
            return true;
        } catch (SocketTimeoutException | PortUnreachableException e) {
            System.out.println("> Server isn't responding");
            return false;
        } catch (IOException e) {
            System.out.println("> Smth wrong with connection");
            return false;
        }
    }

   /* private boolean receive(DatagramPacket packetIn) throws IOException, InterruptedException {
            //получение датаграммы
        try {
            socket.receive(packetIn);
            return true;
        } catch (PortUnreachableException e) {
                Thread.sleep(2000);
                return receive(packetIn);
        }
    }

    */
}



