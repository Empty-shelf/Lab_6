package Commands;

public class Help extends ACommand {

    Help(String name){
        this.name = name;
    }

    @Override
    public String execute() {
        manager.help();
        return null;
    }
}
