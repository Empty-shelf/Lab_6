package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class RemoveById extends ACommand {
    int arg;

    public RemoveById(String name, int arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.remove_by_id(arg);
    }
}
