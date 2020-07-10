package Commands;

import Collection.Route;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Add extends ACommand {
    private Route arg;

    public Add(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }
    @Override
    public ArrayList<String> execute() {
        return manager.add(arg);
    }
}
