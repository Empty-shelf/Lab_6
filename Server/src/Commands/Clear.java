package Commands;

public class Clear extends ACommand {
    private String mess;

    Clear(String name){
      this.name = name;
    }
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.clear();
        return null;
    }
}
