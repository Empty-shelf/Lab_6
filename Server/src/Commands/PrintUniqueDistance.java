package Commands;

import java.util.ArrayDeque;

public class PrintUniqueDistance extends ACommand {

    public PrintUniqueDistance(String name){
        this.name = name;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.print_unique_distance();
    }
}
