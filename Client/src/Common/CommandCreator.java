package Common;
import UI.MultiInputWindow;

import java.util.*;

//класс создания оболочек команд
public class CommandCreator {
    //инициализация объекта, который создает аргумент - элемент коллекции
    private ElementCreator elementCreator = new ElementCreator();
    private CommandShell command;

    public CommandShell create(String name, String login, String arg, MultiInputWindow window, int id) throws IllegalArgumentException{
        command = new CommandShell(name);
        command.setLogin(login);
        switch (name){
            case "update":
            case "add":
            case "add_if_min":
                elementCreator.constructor(id, login, window);
                command.setFirstArg(elementCreator.getRoute());
                command.setSecondArg(id);
            case "remove_by_id":
                command.setSecondArg(id);
            case "filter_contains_name":
                command.setThirdArg(arg);
        }
        return command;
    }

    //для работы со скриптом
    public CommandShell create(String name, int j, ArrayList<String[]> script, String login){
        command = new CommandShell(name);
        command.setLogin(login);
        if (name.equals("add") || name.equals("add_if_min") || name.equals("update"))
            command.setFirstArg(elementCreator.constructor(j, script, login));
        return command;
    }
}