package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Show extends ACommand {

    public Show(String name){
        this.name = name;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.show();
    }
}
