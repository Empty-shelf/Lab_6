package Commands;

import Collection.Route;

public class AddIfMin extends ACommand {
    Route arg;

    public AddIfMin(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.add_if_min(arg);
        return null;
    }
}
