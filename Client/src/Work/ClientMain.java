package Work;

import Common.Commander;
import Connection.*;

public class ClientMain {
    public static void main (String [] args) {
        Commander commander = new Commander(new Manager("localhost", 1234));

        while (true) {
                commander.interactiveMod();
        }
    }
}

