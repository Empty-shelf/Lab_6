package Commands;

public class RemoveById extends ACommand {
    int arg;
    private String mess;
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
