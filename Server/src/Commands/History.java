package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class History extends ACommand {

    public History(String name){
        this.name = name;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.history();
    }
}
