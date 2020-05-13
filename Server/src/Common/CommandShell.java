package Common;

import Collection.Route;

import java.io.Serializable;
import java.util.ArrayDeque;

public class CommandShell implements Serializable{

    private ArrayDeque<String> mess;
    private String name;
    private Route firstArg;
    private int secondArg;
    private String thirdArg;

    public void setMess(ArrayDeque<String> answer){
        this.mess = answer;
    }

    public ArrayDeque<String> getMess(){return this.mess;}

    CommandShell(String name, Route firstArg){
        this.name = name;
        this.firstArg = firstArg;
    }

    CommandShell(String name, int secondArg){
        this.name = name;
        this.secondArg = secondArg;
    }

    CommandShell(String name, String thirdArg){
        this.name = name;
        this.thirdArg = thirdArg;
    }

    CommandShell(String name){
        this.name = name;
    }

    public String getName(){return this.name;}
    public Route getFirstArg(){return this.firstArg;}
    public int getSecondArg(){return this.secondArg;}
    public String getThirdArg(){return this.thirdArg;}

    @Override
    public String toString() {
        return getName() + getFirstArg() + getSecondArg() + getThirdArg();
    }

    public void setName(String name) {this.name = name;}
}
