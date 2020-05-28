package Connection;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection {

    private static final Logger logger = Logger.getLogger(Connection.class.getName());

    private static int PORT;
    private static String HOST = "localhost";
    private DatagramChannel channel;
    private SocketAddress addr1;

    private static Connection connection;

    public static Connection getInstance(int port) throws IOException {
        if (connection == null) {
            connection = new Connection(port);
            return connection;
        } else return connection;
    }

    private Connection(int port) throws IOException{
        PORT = port;
        addr1 = new InetSocketAddress(PORT);
        //привязка (локального) адреса к сокету канала
        channel = DatagramChannel.open().bind(addr1);
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public boolean getConnection(){
        SocketAddress addr2;
        byte[] b = new byte[]{3,2,1};
        try {

            ByteBuffer buf = ByteBuffer.wrap(b);

            //установка времени ожидания для сокета канала
            channel.socket().setSoTimeout(30000);
            //получение адреса клиента и данных
            addr2 = channel.receive(buf);
            logger.log(Level.INFO,"Connecting to " + addr2);

            //подключение сокета канала к адресу клиента
            channel.connect(addr2);
            //сброс для записи данных
            buf.flip();
            channel.write(buf);
            logger.log(Level.INFO, "Completed");
            return true;
        } catch (IOException | NullPointerException e) {
            logger.log(Level.SEVERE, "Input/output error", e);
            return false;
        }
    }
}

