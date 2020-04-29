package Commands;

import Common.Manager;

public abstract class ACommand implements ICommand {
    String name;
    String mess;
    protected Manager manager;
}
