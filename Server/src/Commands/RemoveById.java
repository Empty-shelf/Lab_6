package Commands;

import Common.ACommand;

public class RemoveById extends ACommand {
    int arg;
    @Override
    public void execute() {
        manager.remove_by_id(arg);
    }
}
