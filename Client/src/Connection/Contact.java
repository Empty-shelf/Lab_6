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

 /*   public boolean setContact(){
        byte [] buf = {1,2,3};
        DatagramPacket packet1 = new DatagramPacket(buf,buf.length,addr);
        try {
            socket.send(packet1);
            for(byte b: buf){
                b = 0;
            }
            DatagramPacket packet2 = new DatagramPacket(buf,buf.length);
            socket.setSoTimeout(1000);
            try{
                socket.receive(packet2);
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
  */
}

