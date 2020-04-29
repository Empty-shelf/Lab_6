package Commands;

import java.util.ArrayDeque;

public class Help extends ACommand {

    public Help(String name){
        this.name = name;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.help();
    }
}
