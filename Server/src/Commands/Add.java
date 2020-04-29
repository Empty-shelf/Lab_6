package Commands;

import Collection.Route;

public class Add extends ACommand {
    Route arg;
    String mess;

    public Add(String name, Route arg){
        this.name = name;
        this.arg = arg;
    }
    @Override
    public String execute() {
        return manager.add(arg);
    }

    public void setMess(String mess) {
       this.mess = mess;
    }
}
