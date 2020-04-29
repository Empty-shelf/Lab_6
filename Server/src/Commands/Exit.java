package Commands;

public class Exit extends ACommand {
    private String mess;
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.exit();
        return null;
    }
}
