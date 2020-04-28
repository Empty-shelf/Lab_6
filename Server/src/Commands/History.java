package Commands;

import Common.ACommand;

public class History extends ACommand {
    @Override
    public void execute() {
        manager.history();
    }
}
