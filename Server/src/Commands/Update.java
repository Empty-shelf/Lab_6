package Commands;

import Collection.Route;

import java.util.ArrayDeque;

public class Update extends ACommand {
    Route arg;

    public Update(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.update(arg);
    }
}
