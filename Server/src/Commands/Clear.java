package Commands;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Clear extends ACommand {

    public Clear(String name){
      this.name = name;
    }

    @Override
    public ArrayList<String> execute() {
        return manager.clear();
    }
}
