package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Info extends ACommand {

    public Info(String name){
        this.name = name;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.info();
    }
}
