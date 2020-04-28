package Commands;

import Common.ACommand;

public class RemoveHead extends ACommand {
    @Override
    public void execute() {
        manager.remove_head();
    }
}
