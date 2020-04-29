package Commands;

public class GroupCountingByFrom extends ACommand {
    private String mess;
    GroupCountingByFrom(String name){
        this.name = name;
    }
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.group_counting_by_from();
        return null;
    }
}
