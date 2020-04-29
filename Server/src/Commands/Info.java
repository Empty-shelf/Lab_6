package Commands;

import java.util.ArrayDeque;

public class Info extends ACommand {

    public Info(String name){
        this.name = name;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.info();
    }
}
