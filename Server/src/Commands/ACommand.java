package Commands;

import Common.ExecuteManager;

public abstract class ACommand implements ICommand {
    String name;
    protected ExecuteManager manager = ExecuteManager.getInstance("");
}
