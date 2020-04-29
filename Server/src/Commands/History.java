package Commands;

public class History extends ACommand {
    private String mess;
    History(String name){
        this.name = name;
    }
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.history();
        return null;
    }
}
