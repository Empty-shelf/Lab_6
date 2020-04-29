package Commands;

public class RemoveHead extends ACommand {
    private String mess;
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.remove_head();
        return null;
    }
}
