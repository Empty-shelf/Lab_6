package Work;


import Commands.Add;
import Commands.AddIfMin;
import Common.CommandShell;
import Common.Manager;

import java.io.IOException;

public class Commander {

    private Manager manager;

    Commander(Manager manager){
        this.manager = manager;
    }

    public void start(CommandShell command){
        try {
            if (command == null) throw new IOException();
        }catch (IOException e){
            System.out.println("Bad command received");
        }
        if (command.getName().equals("add")) {
            Add add = new Add(command.getFirstArg());
            add.setMess(add.execute());
        }else if (command.getName().equals("add_if_min")){
            AddIfMin addIfMin = new AddIfMin(command.getFirstArg());
        }
    }
}
