package Commands;

import java.util.ArrayDeque;

public class Clear extends ACommand {

    public Clear(String name){
      this.name = name;
    }

    @Override
    public ArrayDeque<String> execute() {
        return manager.clear();
    }
}
