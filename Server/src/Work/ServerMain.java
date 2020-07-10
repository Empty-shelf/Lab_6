package Work;

import Common.ExecuteManager;
import Connection.*;
import DataBase.WorkBase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ServerMain {
    public static void main(String[] args) {
        WorkBase.load();
        ForkJoinPool FJPool = new ForkJoinPool();
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        while (true) {
            Connection connection = new Connection(2345);
            Sender sender = new Sender(connection);
            sender.setName("Sender");
            Receiver receiver = new Receiver(connection);
            receiver.setName("Receiver");
            ConnectionManager connectionManager = new ConnectionManager(receiver, sender);
            Commander commander = new Commander(ExecuteManager.getInstance(), connectionManager);
            commander.setName("Commander");
            FJPool.submit(ForkJoinTask.adapt(receiver));
            cachedPool.submit(sender);
            cachedPool.submit(commander);
        }
    }
}
