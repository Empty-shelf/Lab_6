import java.io.IOException;
import java.net.*;

public class ClientMain {
    public static void main (String [] args) {
        try {
            byte[] buf = {1, 2, 3, 4, 5};
            SocketAddress addr = new InetSocketAddress("localhost", 1234);
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packetOut = new DatagramPacket(buf, buf.length, addr);
            socket.send(packetOut);
            System.out.println("Sent");
            DatagramPacket packetIn = new DatagramPacket(buf, buf.length);
            socket.receive(packetIn);
            System.out.println("Received");
            for (byte i : buf) {
                System.out.println(i);
            }
        }catch (SocketException e){
            System.out.println("Can't create socket");
        }catch (IOException e){
            System.out.println("input/output error");
        }
    }
}
