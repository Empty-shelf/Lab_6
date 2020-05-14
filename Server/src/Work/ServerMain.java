package Work;

import Common.ExecuteManager;

public class ServerMain {
    public static void main(String[] args) {
        Commander commander = new Commander(ExecuteManager.getInstance(System.getenv("Coll_Path")), 1234);
        while (true) {
            commander.start();
        }
    }
}
