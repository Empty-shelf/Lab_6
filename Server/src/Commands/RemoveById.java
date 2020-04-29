package Commands;

public class RemoveById extends ACommand {
    int arg;

    RemoveById(String name, int arg){
        this.name = name;
        this.arg = arg;
    }

    @Override
    public String execute() {
        manager.remove_by_id(arg);
        return null;
    }
}
