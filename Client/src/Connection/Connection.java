package Connection;

import java.io.IOException;
import java.net.*;
import java.time.ZonedDateTime;

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
    public void setAddr(SocketAddress addr){
        this.addr = addr;
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
            //на вход
            DatagramPacket packetIn = new  DatagramPacket(b, b.length);
            socket.setSoTimeout(20000);
            socket.receive(packetIn);
            addr = packetIn.getSocketAddress();
            socket.connect(addr);
            packetOut.setSocketAddress(addr);
            //соединение с адресом и портом (сервером)
            socket.send(packetOut);
            socket.receive(packetIn);
            socket.disconnect();
            SocketAddress addr_2 = packetIn.getSocketAddress();
            socket.connect(addr_2);
            return true;
        } catch (SocketTimeoutException e) {
            System.out.println("> Server isn't responding");
            return false;
        }catch (PortUnreachableException e){
            return false;
        } catch (IOException e) {
            System.out.println("> Sth wrong with connection");
            return false;
        }
    }
}



