package Connection;

import Common.CommandShell;

import java.io.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    public static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    private DatagramChannel channel;
    private SocketAddress addr1;
    private SocketAddress addr2;
    private static int PORT;

    public ConnectionManager(int port){
        try {
            PORT = port;
            addr1 = new InetSocketAddress(PORT);
            channel = DatagramChannel.open().bind(addr1);
        }catch (IOException e){
            logger.log(Level.SEVERE, "Input/output error", e);
        }
    }

    public CommandShell receive(){
        byte bytes[] = new byte[32*1024];
        ByteBuffer bbf = ByteBuffer.wrap(bytes);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        logger.log(Level.INFO,"Waiting for request..." );
        try {
            bbf.clear();

            addr2 = channel.receive(bbf);

            if (!channel.isConnected()) {
                channel.connect(addr2);
                logger.log(Level.INFO, "Channel is connected to client's address");
            }

            ois = new ObjectInputStream(bais);
            CommandShell command = (CommandShell) ois.readObject();
            return command;
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Input/output error", e);
            return null;
        }
    }

    public void send(CommandShell command){
        byte buf[] = new byte[32*1024];
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        ObjectOutputStream ois = null;
        try {
            ois = new ObjectOutputStream(bais);
            ois.writeObject(command);
            buf = bais.toByteArray();
            ByteBuffer bbf = ByteBuffer.wrap(buf);
            channel.write(bbf);
            logger.log(Level.INFO,"Sent a response");
            if (command.getName().equals("exit")){
                channel.disconnect();
                logger.log(Level.INFO, "Channel is disconnected to client's address");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input/output error", e);
        }
    }
}