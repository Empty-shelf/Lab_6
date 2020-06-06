package Connection;

import Common.CommandShell;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class Sender {

    private Connection connection;

    public Connection Connection(){return this.connection;}

    public Sender(Connection con){
        connection = con;
    }
    private byte b[] = new byte[32*1024];

    //отправка команды на сервер
    public boolean send(CommandShell command){
        byte b2[];
        try {
            //цепочка потоков (на вывод)
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ObjectOutputStream objOutput = new ObjectOutputStream(output);
            //сериализация объекта (для отправки)
            objOutput.writeObject(command);
            //запись сериализованного объекта в байтовый массив
            b2 = output.toByteArray();
            //сброс данных
            output.reset();
            //отправка данных и возврат сообщения об успешности операции
            return sendData(b2);

        } catch (IOException e) {
            System.out.println("> Problem with serialization!");
            return false;
        }
    }

    private boolean sendData(byte[] b) {
        //получение данных о месте назначения
        DatagramSocket socket = connection.getSocket();
        SocketAddress addr = connection.getAddr();
        //инициализация датаграмы (на выход)
        DatagramPacket packet = new DatagramPacket(b, b.length, addr);
        try {
            //отправка датаграмы
            socket.send(packet);
            System.out.println("> Request is sent");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
