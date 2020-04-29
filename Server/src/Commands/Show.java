package Commands;

public class Show extends ACommand {
    private String mess;
    Show(String name){
        this.name = name;
    }
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.show();
        return null;
    }
}
