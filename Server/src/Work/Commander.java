package Work;

import Collection.Route;
import Commands.*;
import Common.CommandShell;
import Common.ExecuteManager;
import Connection.*;
import DataBase.WorkBase;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Commander extends Thread{

    public static final Logger logger = Logger.getLogger(Commander.class.getName());

    boolean isHaveClient = true;
    //исполнитель команд
    private ExecuteManager exManager;
    //команда для отправки
    private CommandShell command;
    private ArrayDeque<String> badMess;
    private  ConnectionManager conManager;

    {
        badMess = new ArrayDeque<>();
        badMess.add("> Command is unavailable, you aren't an owner");
    }

    Commander(ExecuteManager manager, ConnectionManager connectionManager){
        this.conManager = connectionManager;
        this.exManager = manager;
    }

    @Override
    public void run(){
        startSession();
    }



    public void startSession() {
        while (isHaveClient) {
            exManager.clearMess();
            try {
                command = conManager.getReceiver().receive();
                if (command == null) throw new IOException();
                System.out.println("> Command is received");
            }catch (IOException e){
                System.out.println("> Bad command is received");
                e.printStackTrace();
                return;
            }
            switch (command.getName()) {
                case "add":
                    Add add = new Add(command.getName(), command.getFirstArg());
                    command.setMess(add.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "add_if_min":
                    AddIfMin addIfMin = new AddIfMin(command.getName(), command.getFirstArg());
                    command.setMess(addIfMin.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "clear":
                    if(checkOwner(command)){
                        Clear clear = new Clear(command.getName());
                        command.setMess(clear.execute());
                        exManager.addToHistory(command.getName());
                        conManager.getSender().send(command);
                    }else{
                        command.setMess(badMess);
                        conManager.getSender().send(command);
                    }
                    break;
                case "exit":
                    Exit exit = new Exit(command.getName());
                    command.setMess(exit.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    isHaveClient = false;
                    break;
                case "filter_contains_name":
                    FilterContainsName filter = new FilterContainsName(command.getName(), command.getThirdArg());
                    command.setMess(filter.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "group_counting_by_from":
                    GroupCountingByFrom count = new GroupCountingByFrom(command.getName());
                    command.setMess(count.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "help":
                    Help help = new Help(command.getName());
                    command.setMess(help.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "history":
                    History history = new History(command.getName());
                    command.setMess(history.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "info":
                    Info info = new Info(command.getName());
                    command.setMess(info.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "print_unique_distance":
                    PrintUniqueDistance print = new PrintUniqueDistance(command.getName());
                    command.setMess(print.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "remove_by_id":
                    if(checkOwner(command)){
                        RemoveById removeById = new RemoveById(command.getName(), command.getSecondArg());
                        command.setMess(removeById.execute());
                        exManager.addToHistory(command.getName());
                        conManager.getSender().send(command);
                    }else{
                        command.setMess(badMess);
                        conManager.getSender().send(command);
                    }
                    break;
                case "remove_head":
                    if(checkOwner(command)){
                        RemoveHead removeHead = new RemoveHead(command.getName());
                        command.setMess(removeHead.execute());
                        exManager.addToHistory(command.getName());
                        conManager.getSender().send(command);
                    }else{
                        command.setMess(badMess);
                        conManager.getSender().send(command);
                    }
                    break;
                case "show":
                    Show show = new Show(command.getName());
                    command.setMess(show.execute());
                    exManager.addToHistory(command.getName());
                    conManager.getSender().send(command);
                    break;
                case "update":
                    if (checkOwner(command)) {
                        Update update = new Update(command.getName(), command.getFirstArg());
                        command.setMess(update.execute());
                        exManager.addToHistory(command.getName());
                        conManager.getSender().send(command);
                    }else{
                        command.setMess(badMess);
                        conManager.getSender().send(command);
                    }
                    break;
                default:
                    System.out.println("Something went wrong in Commander");
            }
        }
        try {
            conManager.getReceiver().close();
            conManager.getReceiver().getConnection().getChannel().disconnect();
            conManager.getReceiver().getConnection().getChannel().close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input/output error", e);
        }

    }
    private boolean checkOwner(CommandShell com){
        if (com.getName().equals("clear")) {
            if ((long) WorkBase.getRoutes().size() != WorkBase.getRoutes().stream().
                    filter(r -> r.getOwnerLogin().equals(com.getLogin())).count()) {
                return false;
            } else return true;
        }
        if(com.getName().equals("remove_head")){
            if(!WorkBase.getRoutes().getFirst().getOwnerLogin().equals(com.getLogin())){
                return false;
            }else return true;
        }
        if(com.getName().equals("remove_by_id") || com.getName().equals("update")){
            for(Route r : WorkBase.getRoutes()){
                if (r.getId() == com.getSecondArg()) {
                    if(!r.getOwnerLogin().equals(com.getLogin())) return false;
                    else return true;
                }
            }
        }
        return false;
    }
}

