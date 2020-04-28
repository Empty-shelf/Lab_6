package Commands;

import Common.ACommand;

public class Help extends ACommand {
    @Override
    public void execute() {
        manager.help();
    }
}
