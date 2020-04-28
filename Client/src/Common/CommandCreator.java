package Common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

public class CommandCreator {

    private ElementCreator elementCreator = new ElementCreator();

    CommandShell create(String name){
        if (name.equals("add") || name.equals("add_if_min"))
            return new CommandShell(name, elementCreator.constructorSystemIn(0));
        else return new CommandShell(name);
    }

    CommandShell create(String name, int id){
        if (name.equals("update")) return new CommandShell(name, elementCreator.constructorSystemIn(id));
        else return new CommandShell(name, id);
    }

    CommandShell create(String name, String arg){
        return new CommandShell(name, arg);
    }

    //для работы со скриптом
    CommandShell create(String name, int j, ArrayList<String[]> script){
        if (name.equals("add") || name.equals("add_if_min") )
            return new CommandShell(name, elementCreator.constructorFile(j, script));
        else return new CommandShell(name);
    }

}