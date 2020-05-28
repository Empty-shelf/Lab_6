package Connection;

import Common.CommandShell;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receiver {

    private static final Logger logger = Logger.getLogger(Receiver.class.getName());

    private byte b[] = new byte[32*1024];
    private Connection connection;

    public Receiver(Connection con){
        connection = con;
    }

    //возвращает полученные данные в виде объекта
    public CommandShell receive() throws IOException {
        //инициализация канала
        DatagramChannel channel = connection.getChannel();
        //оборачиваем данные в буфер (наш массив байтов)
        ByteBuffer buf = ByteBuffer.wrap(b);
        //инициализация входного потока (источника данных - массив байтов)
        ByteArrayInputStream input = new ByteArrayInputStream(b);
        ObjectInputStream objInput = null;
        logger.log(Level.INFO,"Waiting for request..." );
        try {
            //очистка буфера для следующей записи
            buf.clear();
            //чтение в буфер
            channel.read(buf);
            //инициализация входного потока (для десериализации ранее сериализованных объектов)
            objInput = new ObjectInputStream(input);
            //десериализация объекта
            CommandShell command = (CommandShell) objInput.readObject();
            System.out.println("Received");
            return command;
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "> Bad command is received", e);
            e.printStackTrace();
            return null;
        }
    }
}