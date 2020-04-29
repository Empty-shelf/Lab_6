package Commands;

public class History extends ACommand {

    History(String name){
        this.name = name;
    }

    @Override
    public String execute() {
        manager.history();
        return null;
    }
}
