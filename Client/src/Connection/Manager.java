package Connection;

import Common.CommandCreator;
import Common.CommandShell;
import Common.Users;
import DataBase.SecurePassword;
import org.omg.CORBA.UnknownUserException;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Manager {
    private String login;
    private boolean isLogged = false;
    //отвечает за отправку команды на сервер
    private Sender sender;
    //отвечает за получение ответа от сервера
    private Receiver receiver;

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
            try {
                sender.send(creator.create("exit", login));
                receiver.receive().getMess().forEach(System.out::println);
            }catch (PortUnreachableException e){
                System.out.println("> Port is unreachable");
            }catch (NullPointerException e){
                System.out.println("> No answer from server");
            }
        }));
    }

    //инициализируемая на клиенте и отправляемая на сервер оболочка команды
    private CommandShell com;
    //строка - команда пользователя (введенная с консоли)
    private String userCommand = "";
    //команда пользователя, помещенная в массив (вместе с аргументами)
    private String[] finalUserCommand;
    /*
    контроль рекурсии (коллекция хранит имена файлов,
    содержащих скрипт, которые используются при выполнении одной команды
     */
    private ArrayList<String> rec = new ArrayList<>();
    //отвечает за создание оболочки команды, отправляемой на сервер
    private CommandCreator creator = new CommandCreator();
    private Scanner commandReader = new Scanner(System.in);

    public void interactiveMod() {
        System.out.println("> Ready for work");
        while (!userCommand.equals("exit")) {
            if (commandReader.hasNextLine()) {
                userCommand = commandReader.nextLine();
            }else continue ;
            finalUserCommand = userCommand.trim().split(" ", 2);
            try {
                if (userCommand.trim().length() == 0) throw new InputMismatchException();
                if (finalUserCommand[0].equals("exit")) {
                    commandReader.close();
                    System.exit(0);
                }
                switch (finalUserCommand[0]) {
                    case "help":
                    case "info":
                    case "show":
                    case "group_counting_by_from":
                    case "print_unique_distance":
                    case "history":
                    case "add_if_min":
                    case "clear":
                    case "remove_head":
                    case "add":
                        if (!isLogged) throw new NoSuchElementException();
                        com = creator.create(finalUserCommand[0], login);
                        sender.send(com);
                        check();
                        break;
                    case "update":
                        if (!isLogged) throw new NoSuchElementException();
                        try {
                            int id = Integer.parseInt(finalUserCommand[1].trim());
                            com = creator.create(finalUserCommand[0], id, login);
                            sender.send(com);
                            check();
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("> Input error (id have to be an integer)");
                        }
                        break;
                    case "remove_by_id":
                        if (!isLogged) throw new NoSuchElementException();
                        try {
                            int i = Integer.parseInt(finalUserCommand[1].trim());
                            com = creator.create(finalUserCommand[0], i, login);
                            sender.send(com);
                            check();
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("> Input error (id have to be an integer)");
                            break;
                        }
                    case "filter_contains_name":
                        if (!isLogged) throw new NoSuchElementException();
                        com = creator.create(finalUserCommand[0], finalUserCommand[1].trim(), login);
                        sender.send(com);
                        check();
                        break;
                    case "execute_script":
                        if (!isLogged) throw new NoSuchElementException();
                        script(finalUserCommand[1].trim());
                        break;
                    case "login":
                        System.out.println("> Input your login:");
                        String login_in = commandReader.nextLine().trim();
                        if (!Users.isRegistered(login_in)){
                            System.out.println("> User with this login doesn't exist, try again or register");
                            break;
                        }
                        System.out.println("> Input your password:");
                        String password_in = SecurePassword.generate(commandReader.nextLine().trim());
                        if (Users.isCorrectPassword(login_in, password_in)){
                            Users.addLoggedUser(login_in, password_in);
                            System.out.println("> You have successfully logged in");
                            login = login_in;
                            isLogged = true;
                        }else {
                            System.out.println("> Incorrect password, input 'login' and try again");
                        }
                        break;
                    case "register":
                        System.out.println("> Input your login:");
                        String login_reg = commandReader.nextLine().trim();
                        while (Users.isRegistered(login_reg)) {
                            System.out.println("> This login already exists, create new");
                            login_reg = commandReader.nextLine();
                        }
                        if(login_reg.chars().count()>50){
                            System.out.println("> Login have to be not more than 50 chars");
                            break;
                        }
                        System.out.println("> Input your password:");
                        String password_reg = SecurePassword.generate(commandReader.nextLine());
                        Users.addRegisteredUser(login_reg, password_reg);
                        System.out.println("> You have successfully registered, now you have to login");
                        break;
                    default:
                        System.out.println("> Unidentified command - input 'help' for reference");
                        break;
                }
                } catch (InputMismatchException e) {
                   System.out.println("Empty string entered (unidentified command) - input 'help' for reference");
                } catch (NoSuchElementException e){
                    System.out.println("> You haven't logged yet - input 'login' to do this command");
                }catch (IndexOutOfBoundsException e) {
                    System.out.println("> Missing argument");
                }
        }
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

    private void check(){
        try {
            receiver.receive().getMess().forEach(System.out::println);
        }catch (PortUnreachableException e) {
            try {
                if (connect()) {
                    sender.send(com);
                    receiver.receive().getMess().forEach(System.out::println);
                    System.out.println("> Connection is established");
                } else throw new PortUnreachableException();
            } catch (PortUnreachableException r) {
                System.out.println("> Port is unreachable");
            }
        }
    }


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
            System.out.println("> Recursion cannot work with the same file");
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
                        case "login":
                            if (!Users.isRegistered(script.get(j + 1)[0])) {
                                System.out.println("> User with this login doesn't exist");
                                throw new InputMismatchException();
                            }
                            if (Users.isCorrectPassword(script.get(j + 1)[0], script.get(j + 2)[0])) {
                                Users.addLoggedUser(script.get(j + 1)[0], script.get(j + 2)[0]);
                                System.out.println("> You have successfully logged in");
                                login = script.get(j + 1)[0];
                                isLogged = true;
                            } else {
                                throw new UnknownUserException();
                            }
                            j += 2;
                            break;
                        case "register":
                            while (Users.isRegistered(script.get(j + 1)[0])) {
                                System.out.println("> This login already exists, create new");
                                throw new InputMismatchException();
                            }
                            Users.addRegisteredUser(script.get(j + 1)[0], script.get(j + 2)[0]);
                            System.out.println("> You have successfully registered");
                            j += 2;
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
                }catch (UnknownUserException e){
                    System.out.println("> Incorrect password");
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
}
