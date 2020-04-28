package Commands;

import Common.ACommand;

public class PrintUniqueDistance extends ACommand {
    @Override
    public void execute() {
        manager.print_unique_distance();
    }
}
