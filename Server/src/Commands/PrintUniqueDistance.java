package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class PrintUniqueDistance extends ACommand {

    public PrintUniqueDistance(String name){
        this.name = name;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.print_unique_distance();
    }
}
