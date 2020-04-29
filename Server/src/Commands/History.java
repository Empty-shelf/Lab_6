package Commands;

import java.util.ArrayDeque;

public class History extends ACommand {

    public History(String name){
        this.name = name;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.history();
    }
}
