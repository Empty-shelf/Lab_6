package Commands;

import Common.ACommand;

public class Clear extends ACommand {
    @Override
    public void execute() {
        manager.clear();
    }
}
