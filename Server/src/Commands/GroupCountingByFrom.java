package Commands;

public class GroupCountingByFrom extends ACommand {
    GroupCountingByFrom(String name){
        this.name = name;
    }
    @Override
    public String execute() {
        manager.group_counting_by_from();
        return null;
    }
}
