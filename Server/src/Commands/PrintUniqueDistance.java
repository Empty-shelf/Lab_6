package Commands;

public class PrintUniqueDistance extends ACommand {
    private String mess;
    @Override
    public void setMess(String mess) {
        this.mess = mess;
    }
    @Override
    public String execute() {
        manager.print_unique_distance();
        return null;
    }
}
