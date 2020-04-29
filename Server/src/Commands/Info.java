package Commands;

public class Info extends ACommand {

    Info(String name){
        this.name = name;
    }

    @Override
    public String execute() {
        manager.info();
        return null;
    }
}
