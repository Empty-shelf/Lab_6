package Commands;

import java.util.ArrayList;

public class FilterContainsName extends ACommand {
    String arg;

    public FilterContainsName(String name, String arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public String execute() {
        manager.filter_contains_name(arg);
        return null;
    }
}
