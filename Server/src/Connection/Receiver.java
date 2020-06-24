package Connection;

import Common.CommandShell;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receiver extends Thread{

    private static final Logger logger = Logger.getLogger(Receiver.class.getName());

    private ReentrantLock rLock = new ReentrantLock();

    private byte b[] = new byte[32*1024];

    private Connection connection;
    private DatagramChannel channel;
    private ByteArrayInputStream bais = new ByteArrayInputStream(b);
    private ByteBuffer buf = ByteBuffer.wrap(b);
    private ObjectInputStream ois;
    boolean stop = true;
    boolean received = false;
    CommandShell command;


    public Connection getConnection() {
        return connection;
    }

    public Receiver(Connection con){
        connection = con;
        channel = connection.getChannel();
    }

    public CommandShell receive() {
        while (!received) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "", e);
            }
        }
        rLock.lock();
        CommandShell c = command;
        received = false;
        rLock.unlock();
        return c;
    }

    //возвращает полученные данные в виде объекта
    public CommandShell receiveCommand() throws IOException {
        logger.log(Level.INFO,"Waiting for request..." );
        try {
            //очистка буфера для следующей записи
            buf.clear();
            //чтение в буфер
            read(buf);
            //инициализация входного потока (для десериализации ранее сериализованных объектов)
            ois = new ObjectInputStream(bais); //StreamCorrupted
            //десериализация объекта
            CommandShell command = (CommandShell) ois.readObject();
            ois.close();
            if("exit".equals(command.getName())) stop = false;
            return command;
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "> Bad command is received", e);
            e.printStackTrace();
            return null;
        }
    }

    private void read(ByteBuffer b){
        b.clear();
        bais.reset();
        while(stop){
            try{
                int i = b.position();
                channel.read(b);
                if (b.position() == i){
                    sleep(1000);
                    continue;
                }
                break;
            }catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void close() {
        stop = false;
        try {
            this.join();
        } catch (InterruptedException e) {
           logger.log(Level.SEVERE,"Break waiting end receiving", e);
        }
        try {
            bais.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Closing receiver", e);
        }
    }

    private synchronized void waitThread() {
        try {
            while (received) {
                wait(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (stop) {
            if (received) {
                waitThread();
            } else {
                try {
                    rLock.lock();
                    command = receiveCommand();
                    received = true;
                    rLock.unlock();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Receiving exception", e);
                }
            }
        }
    }
}