package Commands;

import Collection.Route;
import Common.ACommand;

public class Add extends ACommand {
    Route arg;
    Add(Route arg){
        this.arg = arg;
    }
    @Override
    public void execute() {
        manager.add(arg);
    }
}
