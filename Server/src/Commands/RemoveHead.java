package Commands;

import java.util.ArrayDeque;

public class RemoveHead extends ACommand {

    public RemoveHead(String name){
        this.name = name;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.remove_head();
    }
}
