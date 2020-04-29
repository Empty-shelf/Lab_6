package Commands;

import java.util.ArrayDeque;

public class FilterContainsName extends ACommand {
    String arg;

    public FilterContainsName(String name, String arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.filter_contains_name(arg);
    }
}
