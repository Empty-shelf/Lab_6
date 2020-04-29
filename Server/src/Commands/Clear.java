package Commands;

public class Clear extends ACommand {
    private String mess;

    public Clear(String name){
      this.name = name;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        return manager.clear();
    }
}
