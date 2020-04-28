package Commands;

import Common.ACommand;

public class GroupCountingByFrom extends ACommand {
    @Override
    public void execute() {
        manager.group_counting_by_from();
    }
}
