package Commands;

import Collection.Route;
import Common.ACommand;

public class Update extends ACommand {
    Route arg;
    @Override
    public void execute() {
        manager.update(arg);
    }
}
