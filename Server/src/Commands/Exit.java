package Commands;

public class Exit extends ACommand {

    public Exit(String name){
        this.name = name;
    }

    @Override
    public String execute() {
        manager.exit();
        return null;
    }
}
