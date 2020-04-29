package Commands;

public class PrintUniqueDistance extends ACommand {

    PrintUniqueDistance(String name){
        this.name = name;
    }

    @Override
    public String execute() {
        manager.print_unique_distance();
        return null;
    }
}
