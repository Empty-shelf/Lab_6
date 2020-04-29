package Work;

import Commands.*;
import Common.CommandShell;
import Common.Manager;

import java.io.IOException;

import static Work.ServerMain.sender;

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
                command.setMess(add.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "add_if_min":
                AddIfMin addIfMin = new AddIfMin(command.getName(), command.getFirstArg());
                command.setMess(addIfMin.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "clear":
                Clear clear = new Clear(command.getName());
                command.setMess(clear.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "exit":
                Exit exit = new Exit(command.getName());
                exit.execute();
                break;
            case "filter_contains_name":
                FilterContainsName filter = new FilterContainsName(command.getName(), command.getThirdArg());
                command.setMess(filter.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "group_counting_by_from":
                GroupCountingByFrom count = new GroupCountingByFrom(command.getName());
                command.setMess(count.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "help":
                Help help = new Help(command.getName());
                command.setMess(help.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "history":
                History history = new History(command.getName());
                command.setMess(history.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "info":
                Info info = new Info(command.getName());
                command.setMess(info.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "print_unique_distance":
                PrintUniqueDistance print = new PrintUniqueDistance(command.getName());
                command.setMess(print.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "remove_by_id":
                RemoveById removeById = new RemoveById(command.getName(), command.getSecondArg());
                command.setMess(removeById.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "remove_head":
                RemoveHead removeHead = new RemoveHead(command.getName());
                command.setMess(removeHead.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "show":
                Show show = new Show(command.getName());
                command.setMess(show.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            case "update":
                Update update = new Update(command.getName(), command.getFirstArg());
                command.setMess(update.execute());
                manager.clearMess();
                manager.addToHistory(command.getName());
                sender.send(command);
                break;
            default:
                System.out.println("Something goes wrong in Commander");
        }
    }
}
