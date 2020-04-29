package Commands;

public class Clear extends ACommand {

    public Clear(String name){
      this.name = name;
    }

    @Override
    public String execute() {
        return manager.clear();
    }
}
