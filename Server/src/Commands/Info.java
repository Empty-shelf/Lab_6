package Commands;

public class Info extends ACommand {
    private String mess;
    Info(String name){
        this.name = name;
    }
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
