package Work;

import Commands.*;
import Common.CommandShell;
import Common.ExecuteManager;
import Connection.Connection;
import Connection.ConnectionManager;

import Connection.Receiver;
import Connection.Sender;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Commander {

    public static final Logger logger = Logger.getLogger(Commander.class.getName());

    private ExecuteManager exManager;

    private CommandShell command;

    private Connection connection;
    private ConnectionManager conManager;
    private int PORT;

    Commander(ExecuteManager manager, int port){
        try {
            this.exManager = manager;
            connection = Connection.getInstance(port);
        }catch (IOException e){
            logger.log(Level.SEVERE, "Input/output error", e);
        }
    }

    void start(){
        exManager.clearMess();
        connection.getConnection();
        conManager = new ConnectionManager(new Receiver(connection), new Sender(connection));
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
                Clear clear = new Clear(command.getName());
                command.setMess(clear.execute());
                exManager.addToHistory(command.getName());
                conManager.getSender().send(command);
                break;
            case "exit":
                Exit exit = new Exit(command.getName());
                command.setMess(exit.execute());
                exManager.addToHistory(command.getName());
                conManager.getSender().send(command);
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
                RemoveById removeById = new RemoveById(command.getName(), command.getSecondArg());
                command.setMess(removeById.execute());
                exManager.addToHistory(command.getName());
                conManager.getSender().send(command);
                break;
            case "remove_head":
                RemoveHead removeHead = new RemoveHead(command.getName());
                command.setMess(removeHead.execute());
                exManager.addToHistory(command.getName());
                conManager.getSender().send(command);
                break;
            case "show":
                Show show = new Show(command.getName());
                command.setMess(show.execute());
                exManager.addToHistory(command.getName());
                conManager.getSender().send(command);
                break;
            case "update":
                Update update = new Update(command.getName(), command.getFirstArg());
                command.setMess(update.execute());
                exManager.addToHistory(command.getName());
                conManager.getSender().send(command);
                break;
            default:
                System.out.println("Something went wrong in Commander");
        }
        try {
            connection.getChannel().disconnect();
        }catch (IOException e){
            logger.log(Level.SEVERE, "Input/output error", e);
        }
    }
}
