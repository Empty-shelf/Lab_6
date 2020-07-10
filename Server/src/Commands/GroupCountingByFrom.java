package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class GroupCountingByFrom extends ACommand {
    public GroupCountingByFrom(String name){
        this.name = name;
    }
    @Override
    public ArrayList<String> execute() {
        return manager.group_counting_by_from();
    }
}
