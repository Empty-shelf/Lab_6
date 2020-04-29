package Work;


import Commands.*;
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
        switch (command.getName()) {
            case "add":
                Add add = new Add(command.getName(), command.getFirstArg());
                add.setMess(add.execute());
                manager.addToHistory(command.getName());
                break;
            case "add_if_min":
                AddIfMin addIfMin = new AddIfMin(command.getName(), command.getFirstArg());
                addIfMin.setMess(addIfMin.execute());
                manager.addToHistory(command.getName());
                break;
            case "clear":
                Clear clear = new Clear(command.getName());
                clear.setMess(clear.execute());
                manager.addToHistory(command.getName());
                break;
            case "exit":
                Exit exit = new Exit(command.getName());
                exit.execute();
                manager.addToHistory(command.getName());
                break;
            case "filter_contains_name":
                FilterContainsName filter = new FilterContainsName(command.getName(), command.getThirdArg());
                filter.setAnswer(filter.execute());
                //переделать все под стрим апи, потом тут расписывать,
                //возможно рпидется менять тип ответов и месседжей
        }
    }
}
