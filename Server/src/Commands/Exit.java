package Commands;

import java.util.ArrayDeque;

public class Exit extends ACommand {

    public Exit(String name){
        this.name = name;
    }

    @Override
    public ArrayDeque<String> execute() {
        manager.exit();
        return null;
    }
}
