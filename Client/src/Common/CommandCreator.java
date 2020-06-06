package Common;
import java.util.*;

//класс создания оболочек команд
public class CommandCreator {
    //инициализация объекта, который создает аргумент - элемент коллекции
    private ElementCreator elementCreator = new ElementCreator();

    public CommandShell create(String name){
        if (name.equals("add") || name.equals("add_if_min"))
            return new CommandShell(name, elementCreator.constructor(0));
        else return new CommandShell(name);
    }

    public CommandShell create(String name, int id){
        if (name.equals("update")) return new CommandShell(name, elementCreator.constructor(id));
        else return new CommandShell(name, id);
    }

    public CommandShell create(String name, String arg){
        return new CommandShell(name, arg);
    }

    //для работы со скриптом
    public CommandShell create(String name, int j, ArrayList<String[]> script){
        if (name.equals("add") || name.equals("add_if_min") || name.equals("update"))
            return new CommandShell(name, elementCreator.constructor(j, script));
        else return new CommandShell(name);
    }

}