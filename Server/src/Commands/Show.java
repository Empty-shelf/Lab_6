package Commands;

public class Show extends ACommand {

    Show(String name){
        this.name = name;
    }

    @Override
    public String execute() {
        manager.show();
        return null;
    }
}
