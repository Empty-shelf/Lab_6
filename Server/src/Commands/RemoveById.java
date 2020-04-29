package Commands;

public class RemoveById extends ACommand {
    int arg;
    private String mess;
    RemoveById(String name, int arg){
        this.name = name;
        this.arg = arg;
    }
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.remove_by_id(arg);
        return null;
    }
}
