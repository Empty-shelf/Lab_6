package Commands;

import Common.ACommand;

public class Exit extends ACommand {
    @Override
    public void execute() {
        manager.exit();
    }
}
