package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class RemoveHead extends ACommand {

    public RemoveHead(String name){
        this.name = name;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.remove_head();
    }
}
