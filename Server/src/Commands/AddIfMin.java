package Commands;

import Collection.Route;
import Common.ACommand;

public class AddIfMin extends ACommand {
    Route arg;
    @Override
    public void execute() {
        manager.add_if_min(arg);
    }
}
