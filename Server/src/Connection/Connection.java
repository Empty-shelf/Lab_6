package Connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection {

    private static final Logger logger = Logger.getLogger(Connection.class.getName());

    private static int PORT = 2345;
    private static DatagramChannel mainChannel;
    private static SocketAddress addr;

    static{
        try {
            mainChannel = DatagramChannel.open().bind(new InetSocketAddress(PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatagramChannel getChannel() {
        return channel_2;
    }

    private DatagramChannel channel_2;

    public Connection(int port){
        PORT = port;
        addr = new InetSocketAddress(PORT);
        try {
            channel_2 = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getConnection();

    }

    //установление связи
    public boolean getConnection(){
        byte[] b = new byte[]{3,2,1};
        try {

            channel_2 =  DatagramChannel.open();

            ByteBuffer buf = ByteBuffer.wrap(b);

            //установка времени ожидания для сокета канала
            mainChannel.socket().setSoTimeout(30000);
            //получение адреса клиента и данных
            addr = mainChannel.receive(buf);
            logger.log(Level.INFO,"Connecting to " + addr);
            //привязка (локального) адреса к сокету канала
            channel_2.bind(new InetSocketAddress(0));
            System.out.println(channel_2.socket().getLocalPort());
            //подключение сокета канала к адресу клиента
            channel_2.connect(addr);
            //сброс для записи данных
            buf.flip();
            channel_2.write(buf);
            buf.clear();
            channel_2.receive(buf);
            buf.flip();
            channel_2.write(buf);
            logger.log(Level.INFO, "Completed");
            channel_2.configureBlocking(false);
            return true;
        } catch (IOException | NullPointerException e) {
            logger.log(Level.SEVERE, "Input/output error", e);
            return false;
        }
    }
}

