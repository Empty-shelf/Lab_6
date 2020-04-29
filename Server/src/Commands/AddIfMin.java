package Commands;

import Collection.Route;

public class AddIfMin extends ACommand {
    Route arg;
    String mess;

    public AddIfMin(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        return manager.add_if_min(arg);
    }
}
