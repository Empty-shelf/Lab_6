import java.io.IOException;
import java.net.*;

public class ServerMain {
    public static void main(String[] args) {
        try {
            byte[] buf = new byte[5];
            System.out.println("Waiting...");
            DatagramSocket socket = new DatagramSocket(1234);
            DatagramPacket packetIn = new DatagramPacket(buf, buf.length);
            socket.receive(packetIn);
            for (int i = 0; i < 5; i++) {
                buf[i] *= 2;
            }
            System.out.println("Received");
            System.out.println(packetIn);
            DatagramPacket packetOut = new DatagramPacket(buf, buf.length, packetIn.getAddress(), packetIn.getPort());
            socket.send(packetOut);
            System.out.println("Sent");
            socket.close();
        }catch (SocketException e){
            System.out.println("Can't create socket");
        }catch (IOException e){
            System.out.println("input/output error");
        }
    }
}