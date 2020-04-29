package Commands;

public class FilterContainsName extends ACommand {
    String arg;
    private String mess;
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.filter_contains_name(arg);
        return null;
    }
}
