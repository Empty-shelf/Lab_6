package Commands;

import Common.Manager;

public abstract class ACommand implements ICommand {
    String name;
    protected Manager manager;
}
