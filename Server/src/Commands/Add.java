package Commands;

import Collection.Route;
import java.util.ArrayDeque;

public class Add extends ACommand {
    private Route arg;

    public Add(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }
    @Override
    public ArrayDeque<String> execute() {
        return manager.add(arg);
    }
}
