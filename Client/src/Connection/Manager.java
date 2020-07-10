package Connection;

import Common.CommandCreator;
import Common.CommandShell;
import UI.AnswerWindow;
import UI.InfoWindow;
import UI.MultiInputWindow;
import UI.SingleInputWindow;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.*;

public class Manager {
    private String login;
    private Sender sender;
    //отвечает за получение ответа от сервера
    private Receiver receiver;

    public Sender getSender(){
        return sender;
    }

    public Receiver getReceiver(){
        return receiver;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    //реализация "объекта-одиночки"
    private static Manager manager;
    //метод инициализации/получения "объекта-одиночки"
    public static Manager getInstance(Receiver receiver, Sender sender){
            manager = new Manager(receiver, sender);
            return manager;
    }

    private Manager(Receiver receiver, Sender sender){
        this.receiver = receiver;
        this.sender = sender;
        //обработка ситуации отключения jvm
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            sender.send(creator.create("exit", login, "", null, 0));
        }));
    }

    //инициализируемая на клиенте и отправляемая на сервер оболочка команды
    private CommandShell com;

    /*
    контроль рекурсии (коллекция хранит имена файлов,
    содержащих скрипт, которые используются при выполнении одной команды
     */
    private ArrayList<String> rec = new ArrayList<>();
    //отвечает за создание оболочки команды, отправляемой на сервер
    private CommandCreator creator = new CommandCreator();
    private SingleInputWindow single_input_window;
    private MultiInputWindow  multi_input_window;
    private InfoWindow info_window;
    private AnswerWindow answer_window;

    public void execute(String name) {
        try{
        switch (name) {
            case "help":
            case "info":
            case "show":
            case "group_counting_by_from":
            case "print_unique_distance":
            case "history":
            case "clear":
            case "remove_head":
                com = creator.create(name, login, "", null, 0);
                sender.send(com);
                answer_window = new AnswerWindow(check());
                break;
            case "add":
            case "add_if_min":
                multi_input_window = new MultiInputWindow();
                multi_input_window.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        com = creator.create(name, login, "", multi_input_window, 0);
                        if (com.getFirstArg()==null) return;
                        sender.send(com);
                        answer_window = new AnswerWindow(check());
                    }
                });
                break;
            case "update":
                single_input_window = new SingleInputWindow();
                single_input_window.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        multi_input_window = new MultiInputWindow();
                        multi_input_window.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    int id = Integer.parseInt(single_input_window.getString());
                                    com = creator.create(name, login, "", multi_input_window, id);
                                    if (com.getFirstArg()==null) return;
                                    sender.send(com);
                                    answer_window = new AnswerWindow(check());
                                } catch (NumberFormatException er) {
                                    info_window = new InfoWindow(" Input error (id have to be an integer) ", "error");
                                }
                            }
                        });
                    }
                });
                break;
            case "remove_by_id":
                single_input_window = new SingleInputWindow();
                single_input_window.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            int id = Integer.parseInt(single_input_window.getString());
                            com = creator.create(name, login,"", null, id);
                            sender.send(com);
                            answer_window = new AnswerWindow(check());
                        } catch (NumberFormatException er) {
                            info_window = new InfoWindow(" Input error (id have to be an integer) ", "error");
                        }
                    }
                });
                break;
            case "filter_contains_name":
                single_input_window = new SingleInputWindow();
                single_input_window.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        com = creator.create(name, login, single_input_window.getString(), null, 0);
                        sender.send(com);
                        answer_window = new AnswerWindow(check());
                    }
                });
                break;
           /* case "execute_script":
                single_input_window = new SingleInputWindow();
                single_input_window.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        script(single_input_window.getString());
                        info_window = new InfoWindow(" Script is executed ", "info");
                    }
                });
                break;

            */
        }

        } catch (InputMismatchException e) {
            info_window = new InfoWindow(" Empty string entered (unidentified command) - " +
                    "input 'help' for reference ", "info");
        } catch (NoSuchElementException e){
            info_window = new InfoWindow(" You haven't logged yet - input 'login' to do this command ",
                    "info");
        }catch (IndexOutOfBoundsException e) {
            info_window = new InfoWindow(" Missing argument ", "error");
        }catch (IllegalArgumentException ignored){}
        com = null;
}

    public boolean connect(){
        int count = 0;
        while(true) {
            try {
                if (sender.getConnection().getConnection()) {
                    System.out.println("> Contact with server: true");
                    return true;
                }else {
                    count++;
                    Thread.sleep(2000);
                    if (count == 15){
                        System.out.println("> Server isn't responding");
                        return false;
                    }
                    if(count==1){
                        manager.sender.getConnection().setAddr(new InetSocketAddress( "localhost", 2345));
                        manager.sender.getConnection().getSocket().disconnect();
                        System.out.println("Wait...");
                    }
                }
            } catch (InterruptedException | NullPointerException v) {
                System.out.println("> Sth went wrong");
                return false;
            }
        }
    }

    public synchronized ArrayList<String> check(){
        try {
            return receiver.receive().getMess();
        }catch (PortUnreachableException | NullPointerException e) {
            try {
                if (connect()) {
                    sender.send(com);

                    return receiver.receive().getMess();
                } else throw new PortUnreachableException();
            } catch (PortUnreachableException r) {
                System.out.println("> Port is unreachable");
            }catch (NullPointerException r){
                System.out.println("Server isn't responding");
            }
        }
        return null;
    }

/*
    //выполнение скрипта
    private void script(String file_path){
        File file = new File(file_path);
        try {
            for (String str : rec) {
                if (file.getName().equals(str)) {
                    throw new InputMismatchException();
                }
            }
        }catch (InputMismatchException e){
            info_window = new InfoWindow(" Recursion cannot work with the same file ", "error");
            return;
        }
        rec.add(file.getName());
        try {
            ArrayList<String[]> script = new ArrayList<>();
            Scanner in = new Scanner(new File(file_path));
            String s;
            while (in.hasNextLine()) {
                s = in.nextLine();
                script.add(s.split(" ", 2));
            }
            for (int j = 0; j < script.size(); j++) {
                try {
                    switch (script.get(j)[0]) {
                        case "help":
                        case "info":
                        case "show":
                        case "add":
                        case "clear":
                        case "group_counting_by_from":
                        case "print_unique_distance":
                        case "remove_head":
                        case "add_if_min":
                        case "history":
                            sender.send(creator.create(script.get(j)[0], j, script, login));
                            check();
                            if (script.get(j)[0].equals("exit")) System.exit(0);
                            break;
                        case "update":
                            try {
                                int id = Integer.parseInt(script.get(j)[1].trim());
                                sender.send(creator.create(script.get(j)[0], j, script, login));
                                check();
                            } catch (NumberFormatException e) {
                                System.out.println("> Input error (id have to be an integer)");
                                return;
                            }
                            break;
                        case "remove_by_id":
                            try {
                                int k = Integer.parseInt(script.get(j)[1].trim());
                                sender.send(creator.create(script.get(j)[0], k, login));
                                check();
                            } catch (NumberFormatException e) {
                                System.out.println("> Input error (id have to be an integer)");
                                return;
                            }
                            break;
                        case "execute_script":
                            script(script.get(j)[1].trim());
                            break;
                        case "filter_contains_name":
                            sender.send(creator.create(script.get(j)[0], script.get(j)[1].trim(), login));
                            check();
                            break;
                        default:
                            System.out.println("> Unidentified command");
                            break;
                    }
                    if (script.get(j)[0].equals("add") || script.get(j)[0].equals("add_if_min") ||
                            script.get(j)[0].equals("update")) j += 11;
                } catch (NumberFormatException | InputMismatchException e) {
                    System.out.println("> Wrong format, script is'n fully executed, check your file\n" +
                            "> \u001B[32mReference:\u001B[0m\n\u001B[31mfraction :\u001B[0m distance, " +
                            "x coordinate \u001B[31m(have to be more than -808)\u001B[0m," +
                            " coordinates of locations(from/to)\n\u001B[31minteger :\u001B[0m y coordinate\n" +
                            "\u001B[31mstring (not null) :\u001B[0m route's name (not empty), " +
                            "locations'(from/to) names\n" +
                            "(if you use id like an argument - it \u001B[31mhave to be an integer\u001B[0m)\n" +
                            "> If your login does not exist - you need to register, if it isn't correct or already exist" +
                            " - change it (not more than 50 chars)\n");
                    return;
                } catch (NoSuchElementException e){
                    System.out.println("You haven't logged yet");
                    return;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("> Missing argument, script is'n fully executed");
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("> File (script) not found");
        }catch (NullPointerException e){
            System.out.println("> Smth went wrong in manager");
        }
        rec.clear();
    }

 */
}
