package Connection;

import Common.CommandCreator;
import Common.CommandShell;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.util.*;

public class Sender {
    byte buf[];
    public void send(CommandShell command){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(command);
            buf = baos.toByteArray();
            baos.reset();


        }catch () {

        }
        }
    }
}