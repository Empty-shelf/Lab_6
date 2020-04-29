package Commands;

import java.util.ArrayDeque;

public class GroupCountingByFrom extends ACommand {
    public GroupCountingByFrom(String name){
        this.name = name;
    }
    @Override
    public ArrayDeque<String> execute() {
        return manager.group_counting_by_from();
    }
}
