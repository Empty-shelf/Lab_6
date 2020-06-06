package Connection;

import Common.CommandShell;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender {

    private static final Logger logger = Logger.getLogger(Sender.class.getName());

    private Connection connection;
    public Sender(Connection con){
        connection = con;
    }
    private byte b[] = new byte[32*1024];

    //отправка ответа клиенту
    public boolean send(CommandShell command){
        //инициализация выходного потока (место вывода - массив байтов)
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream objOut = null;
        try {
            //инициализация выходного потока (для сериализации объектов в поток)
            objOut = new ObjectOutputStream(output);
            //сериализация объекта
            objOut.writeObject(command);
            //запись (сериализованных данных) в байтовый массив (из потока)
            b = output.toByteArray();
            //оборачиваем данные в буфер (наш массив байтов)
            ByteBuffer buf = ByteBuffer.wrap(b);
            //отправляем данные по каналу
            connection.getChannel().write(buf);
            logger.log(Level.INFO,"Sent a response");
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input/output error", e);
            return false;
        }

    }
}