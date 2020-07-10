package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Exit extends ACommand {

    public Exit(String name){
        this.name = name;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.exit();
    }
}
