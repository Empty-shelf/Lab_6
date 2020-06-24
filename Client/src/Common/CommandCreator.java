package Common;
import java.util.*;

//класс создания оболочек команд
public class CommandCreator {
    //инициализация объекта, который создает аргумент - элемент коллекции
    private ElementCreator elementCreator = new ElementCreator();
    private CommandShell command;

    public CommandShell create(String name, String login){
        command = new CommandShell(name);
        command.setLogin(login);
        if (name.equals("add") || name.equals("add_if_min"))
            command.setFirstArg(elementCreator.constructor(0, login));
        return command;
    }

    public CommandShell create(String name, int id, String login){
        command = new CommandShell(name);
        command.setLogin(login);
        if (name.equals("update")) command.setFirstArg(elementCreator.constructor(id, login));
        else command.setSecondArg(id);
        return command;
    }

    public CommandShell create(String name, String arg, String login){
        command = new CommandShell(name);
        command.setLogin(login);
        command.setThirdArg(arg);
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