package Commands;

import Collection.Route;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Update extends ACommand {
    Route arg;

    public Update(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.update(arg);
    }
}
