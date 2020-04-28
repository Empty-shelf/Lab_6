package Connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Contact {

    private DatagramChannel channel;
    private SocketAddress addr1;
    private SocketAddress addr2;
    private static int PORT = 1234;

    public DatagramChannel getChannel() {
        return channel;
    }

    public Contact(int port) throws IOException {
        PORT = port;
        addr1 = new InetSocketAddress(PORT);
        channel = DatagramChannel.open();
    }

    public boolean receiveConnection() {
        byte buf[] = {3,2,1};
        try {
            channel.bind(addr1);
            ByteBuffer f = ByteBuffer.wrap(buf);
            f.clear();
            int mesLength = 0;
            channel.socket().setSoTimeout(65535);
            addr2 = channel.receive(f);

            channel.connect(addr2);
            f.flip();
            channel.write(f);

            channel.socket().setSoTimeout(1000);
            return true;
        } catch (IOException | NullPointerException e) {

            return false;
        }

    }

}
