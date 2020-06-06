package Commands;

import Collection.Route;
import java.util.ArrayDeque;

public class AddIfMin extends ACommand {
    private Route arg;

    public AddIfMin(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.add_if_min(arg);
    }
}
