package Commands;

import Common.ACommand;

public class FilterContainsName extends ACommand {
    String arg;
    @Override
    public void execute() {
        manager.filter_contains_name(arg);
    }
}
