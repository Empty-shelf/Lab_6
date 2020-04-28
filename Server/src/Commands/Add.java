package Commands;

import Collection.Route;
import Common.ACommand;

public class Add extends ACommand {
    Route arg;
    @Override
    public void execute() {
        manager.add(arg);
    }
}
