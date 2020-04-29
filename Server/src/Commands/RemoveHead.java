package Commands;

public class RemoveHead extends ACommand {
    private String mess;
    RemoveHead(String name){
        this.name = name;
    }
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
