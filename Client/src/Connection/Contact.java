package Connection;

import Common.CommandShell;
import java.io.IOException;
import java.net.*;

public class Contact {
    private int PORT;
    private String HOST;
    private SocketAddress addr;
    private DatagramSocket socket;

    SocketAddress getAddr() {
        return addr;
    }
    DatagramSocket getSock() {
        return socket;
    }

    public Contact(int port, String host) throws SocketException {
        PORT = port;
        HOST = host;
        addr = new InetSocketAddress(host, port);
        socket = new DatagramSocket();
    }
}

