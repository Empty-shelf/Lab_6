package Commands;

import Common.ACommand;

public class Show extends ACommand {
    @Override
    public void execute() {
        manager.show();
    }
}
