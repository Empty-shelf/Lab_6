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

    public boolean setContact(){
        byte [] bytes = {1,2,3};
        CommandShell com = new CommandShell();
        DatagramPacket packet = new DatagramPacket(bytes,bytes.length,addr);
        try {
            socket.send(packet);
            for(byte b: bytes){
                b = 0;
            }
            DatagramPacket dp = new DatagramPacket(bytes,bytes.length);
            socket.setSoTimeout(1000);
            try{
                socket.receive(dp);
                socket.connect(addr);
                return true;
            }catch (SocketTimeoutException e){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
