package Work;

import Collection.Route;
import Commands.*;
import Common.CommandShell;
import Common.ExecuteManager;
import Connection.*;
import DataBase.WorkBase;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Commander extends Thread{

    public static final Logger logger = Logger.getLogger(Commander.class.getName());

    boolean isHaveClient = true;
    //исполнитель команд
    private ExecuteManager exManager;
    //команда для отправки
    private CommandShell command;
    private ArrayList<String> badMess;
    private  ConnectionManager conManager;

    {
        badMess = new ArrayList<>();
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
                System.out.println(command.getName().toUpperCase());
                if (command == null) throw new IOException();
                System.out.println("> Command is received");
            }catch (IOException e){
                System.out.println("> Bad command is received");
                e.printStackTrace();
                return;
            }
            switch (command.getName()) {
                case "get_routes":
                    ArrayList<String> routes = new ArrayList<>();
                    for(Route r : WorkBase.getRoutes()) {
                        routes.add(r.getId()+","+r.getDistance()+","+r.getName()+","+r.getCoordinates().getX()+","+
                                r.getCoordinates().getY()+","+r.getFrom().getName()+","+r.getFrom().getX()+","+
                                r.getFrom().getY()+","+r.getTo().getName()+","+r.getTo().getX()+","+r.getTo().getY()+
                                ","+r.getOwnerLogin());
                    }
                    command.setMess(routes);
                    conManager.getSender().send(command);
                    break;
                case "sort":
                    String [] args = command.getThirdArg().split(",");
                    ArrayList<String> listS = new ArrayList<>(); Set<String> setS = new HashSet<>();
                    ArrayList<Double> listD = new ArrayList<>(); Set<Double> setD = new HashSet<>();
                    ArrayList<Float> listF = new ArrayList<>(); Set<Float> setF = new HashSet<>();
                    ArrayList<Integer> listI = new ArrayList<>(); Set<Integer> setI = new HashSet<>();
                    ArrayList<Route> coll = WorkBase.getRoutes(); ArrayList<Route> coll2 = new ArrayList<>();
                    switch (args[0]){
                        case"to_x":
                            for (Route route : coll) {
                                listD.add(route.getTo().getX());
                            }
                            setD = new HashSet<>(listD);
                            listD.clear();
                            listD.addAll(setD);
                            Collections.sort(listD);
                            for (int i = 0; i < listD.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getTo().getX()==(listD.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"to_y":
                            for (Route route : coll) {
                                listF.add(route.getTo().getY());
                            }
                            setF = new HashSet<>(listF);
                            listF.clear();
                            listF.addAll(setF);
                            Collections.sort(listF);
                            for (int i = 0; i < listF.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getTo().getY()==(listF.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"coord_y":
                            for (Route route : coll) {
                                listI.add(route.getCoordinates().getY());
                            }
                            setI = new HashSet<>(listI);
                            listI.clear();
                            listI.addAll(setI);
                            Collections.sort(listI);
                            for (int i = 0; i < listI.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getCoordinates().getY().equals(listI.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"from_x":
                            for (Route route : coll) {
                                listD.add(route.getFrom().getX());
                            }
                            setD = new HashSet<>(listD);
                            listD.clear();
                            listD.addAll(setD);
                            Collections.sort(listS);
                            for (int i = 0; i < listD.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getFrom().getX()==(listD.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"from_y":
                            for (Route route : coll) {
                                listF.add(route.getFrom().getY());
                            }
                            setF = new HashSet<>(listF);
                            listF.clear();
                            listF.addAll(setF);
                            Collections.sort(listF);
                            for (int i = 0; i < listF.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getFrom().getY()==(listF.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"coord_x":
                            for (Route route : coll) {
                                listD.add(route.getCoordinates().getX());
                            }
                            setD = new HashSet<>(listD);
                            listD.clear();
                            listD.addAll(setD);
                            Collections.sort(listD);
                            for (int i = 0; i < listD.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getCoordinates().getX()==(listD.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"distance":
                            for (Route route : coll) {
                                listD.add(route.getDistance());
                            }
                            setD = new HashSet<>(listD);
                            listD.clear();
                            listD.addAll(setD);
                            Collections.sort(listD);
                            for (int i = 0; i < listD.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getDistance()==(listD.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"id":
                            for (Route route : coll) {
                                listI.add(route.getId());
                            }
                            setI = new HashSet<>(listI);
                            listI.clear();
                            listI.addAll(setI);
                            Collections.sort(listI);
                            for (int i = 0; i < listI.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getId()==(listI.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"loc_to":
                            for (Route route : coll) {
                                listS.add(route.getTo().getName());
                            }
                            setS = new HashSet<>(listS);
                            listS.clear();
                            listS.addAll(setS);
                            Collections.sort(listS);
                            for (int i = 0; i < listS.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getTo().getName().equals(listS.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"owner":
                            for (Route route : coll) {
                                listS.add(route.getOwnerLogin());
                            }
                            setS = new HashSet<>(listS);
                            listS.clear();
                            listS.addAll(setS);
                            Collections.sort(listS);
                            for (int i = 0; i < listS.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getOwnerLogin().equals(listS.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"loc_from":
                            for (Route route : coll) {
                                listS.add(route.getFrom().getName());
                            }
                            setS = new HashSet<>(listS);
                            listS.clear();
                            listS.addAll(setS);
                            Collections.sort(listS);
                            for (int i = 0; i < listS.size(); i++) {
                                for (Route s : coll) {
                                    if (s.getFrom().getName().equals(listS.get(i))) {
                                        coll2.add(s);
                                    }
                                }
                            }
                            break;
                        case"name":
                            System.out.println(1);
                            for (Route route : coll) {
                                listS.add(route.getName());
                            }
                            System.out.println(2);
                            setS = new HashSet<>(listS);
                            listS.clear();
                            listS.addAll(setS);
                            System.out.println(3);
                            Collections.sort(listS);
                            System.out.println(4);
                            for (int i = 0; i < listS.size(); i++) {
                                System.out.println("for_1");
                                for (Route s : coll) {
                                    System.out.println("for_2");
                                    if (s.getName().equals(listS.get(i))) {
                                        System.out.println("if");
                                        coll2.add(s);
                                    }
                                }
                            }
                            System.out.println(5);
                            break;
                    }
                    if(args[1].equals("ascending")) {
                        WorkBase.setRoutes(coll2);
                    }else{
                        coll.clear();
                        for (int i = coll2.size()-1; i >=0; i--) {
                            coll.add(coll2.get(i));
                        }
                        WorkBase.setRoutes(coll);
                    }
                    break;
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
                    System.out.println("come to case remove");
                    if(checkOwner(command)){
                        System.out.println("case 1");
                        RemoveById removeById = new RemoveById(command.getName(), command.getSecondArg());
                        System.out.println("case 2");
                        command.setMess(removeById.execute());
                        System.out.println("case 3");
                        exManager.addToHistory(command.getName());
                        System.out.println("case 4");
                        conManager.getSender().send(command);
                        System.out.println("case 5");
                    }else{
                        System.out.println("case 6");
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
        System.out.println("checkOwner 0");
        if (com.getName().equals("clear")) {
            if ((long) WorkBase.getRoutes().size() != WorkBase.getRoutes().stream().
                    filter(r -> r.getOwnerLogin().equals(com.getLogin())).count()) {
                return false;
            } else return true;
        }
        if(com.getName().equals("remove_head")){
            if(!WorkBase.getRoutes().get(0).getOwnerLogin().equals(com.getLogin())){
                return false;
            }else return true;
        }
        if(com.getName().equals("remove_by_id") || com.getName().equals("update")){
            System.out.println("checkOwner 1");
            for(Route r : WorkBase.getRoutes()){
                System.out.println("checkOwner 2");
                if (r.getId() == com.getSecondArg()) {
                    System.out.println("checkOwner 3");
                    if(!r.getOwnerLogin().equals(com.getLogin())) return false;
                    else return true;
                }
                System.out.println("checkOwner 4");
            }
        }
        return false;
    }
}

