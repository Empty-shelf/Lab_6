package Commands;

import Common.ACommand;

public class Info extends ACommand {
    @Override
    public void execute() {
        manager.info();
    }
}
