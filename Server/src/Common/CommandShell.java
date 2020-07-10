package Common;

import Collection.Route;
import DataBase.SecurePassword;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class CommandShell implements Serializable{

    private String login;

    private ArrayList<String> mess;
    private String name;
    private Route firstArg;
    private int secondArg;
    private String thirdArg;

    public CommandShell(String name){
        this.name = name;
    }

    public void setLogin(String login){this.login = login;}
    public String getLogin(){return this.login;}
    public void setFirstArg(Route firstArg){this.firstArg = firstArg;}
    public void setSecondArg(int secondArg){this.secondArg = secondArg;}
    public void setThirdArg(String thirdArg){this.thirdArg = thirdArg;}
    public void setMess(ArrayList<String> answer){
        this.mess = answer;
    }
    public ArrayList<String> getMess(){return this.mess;}
    public String getName(){return this.name;}
    public Route getFirstArg(){return this.firstArg;}
    public int getSecondArg(){return this.secondArg;}
    public String getThirdArg(){return this.thirdArg;}
    public void setName(String name) {this.name = name;}
}