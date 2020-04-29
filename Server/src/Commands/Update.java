package Commands;

import Collection.Route;

public class Update extends ACommand {
    Route arg;

    Update(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public String execute() {
        manager.update(arg);
        return null;
    }
}
