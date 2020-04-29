package Commands;

import Collection.Route;

public class AddIfMin extends ACommand {
    Route arg;

    public AddIfMin(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public String execute() {
        return manager.add_if_min(arg);
    }
}
