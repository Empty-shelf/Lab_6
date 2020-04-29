package Commands;

public class Info extends ACommand {
    private String mess;
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.info();
        return null;
    }
}
