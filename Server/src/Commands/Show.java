package Commands;

import java.util.ArrayDeque;

public class Show extends ACommand {

    public Show(String name){
        this.name = name;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.show();
    }
}
