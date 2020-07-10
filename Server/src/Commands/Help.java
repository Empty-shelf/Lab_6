package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Help extends ACommand {

    public Help(String name){
        this.name = name;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.help();
    }
}
