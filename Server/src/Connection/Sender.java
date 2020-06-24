package Connection;

import Common.CommandShell;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender extends Thread{

    private static final Logger logger = Logger.getLogger(Sender.class.getName());

    private Connection connection;
    private DatagramChannel channel;

    public Sender(Connection con){
        connection = con;
        channel = connection.getChannel();
    }

    private byte b[] = new byte[32*1024];
    private boolean haveMes = false;
    private boolean sent = true;
    private boolean work = true;
    CommandShell command;

    //отправка ответа клиенту
    public boolean send(CommandShell command){
        this.command = command;
        haveMes = true;
        sent = false;
        while (!sent) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "", e);
            }
        }
        haveMes = false;
        return true;
    }

    private synchronized void waitThread(){
        try {
            while (!haveMes){
                sleep(1000);
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"", e);
        }
    }

    @Override
    public void run() {
        while (work){
            if(haveMes){
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     ObjectOutputStream oos = new ObjectOutputStream(baos)){
                    oos.writeObject(command);
                    b = baos.toByteArray();
                    ByteBuffer bbf = ByteBuffer.wrap(b);
                    channel.write(bbf);
                    sent = true;
                    haveMes = false;
                    logger.log(Level.INFO, "Sent");
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Serialization, send", e);
                }
            }else {
                waitThread();
            }

        }
    }
}