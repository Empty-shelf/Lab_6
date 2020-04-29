package Commands;

public class RemoveHead extends ACommand {

    RemoveHead(String name){
        this.name = name;
    }

    @Override
    public String execute() {
        manager.remove_head();
        return null;
    }
}
