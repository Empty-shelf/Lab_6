package Commands;

import Collection.Route;

public class Update extends ACommand {
    Route arg;
    private String mess;
    Update(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }

    @Override
    public String execute() {
        manager.update(arg);
        return null;
    }
}
