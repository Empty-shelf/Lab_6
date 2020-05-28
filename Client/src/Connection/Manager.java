package Connection;

import Common.CommandCreator;
import Common.CommandShell;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Manager {

    private Sender sender;
    private Receiver receiver;

    private DatagramSocket sock;
    private SocketAddress addr;

    private static Manager manager;

    public static Manager getInstance(Receiver receiver, Sender sender){
        if (manager == null) {
            manager = new Manager(receiver, sender);
            return manager;
        } else return manager;
    }

    private Manager(Receiver receiver, Sender sender){
        this.receiver = receiver;
        this.sender = sender;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sender.send(creator.create("exit"));
                receiver.receive().getMess().forEach(System.out::println);
            }catch (PortUnreachableException e){
                System.out.println("> Port is unreachable");
            }
        }));
    }

    CommandShell com;
    private String userCommand = "";
    private String[] finalUserCommand;
    private ArrayList<String> rec = new ArrayList<>();

    private CommandCreator creator = new CommandCreator();

    private Scanner commandReader = new Scanner(System.in);

    public void interactiveMod() {
        System.out.println("> Ready for work");
        try{
            System.out.println("Contact with server: " + sender.Connection().getConnection());

            while (!userCommand.equals("exit")) {
                boolean check = true;
                while(check) {
                    if (commandReader.hasNextLine()) {
                        userCommand = commandReader.nextLine();
                        check = false;
                    }
                }
                finalUserCommand = userCommand.trim().split(" ", 2);
                try {
                    if (finalUserCommand[0].equals("exit")) { commandReader.close(); System.exit(0);}
                    if (finalUserCommand[0].equals("help") || finalUserCommand[0].equals("info") ||
                            finalUserCommand[0].equals("show") || finalUserCommand[0].equals("add") ||
                            finalUserCommand[0].equals("clear") ||
                            finalUserCommand[0].equals("group_counting_by_from") ||
                            finalUserCommand[0].equals("print_unique_distance") ||
                            finalUserCommand[0].equals("remove_head") || finalUserCommand[0].equals("add_if_min") ||
                            finalUserCommand[0].equals("history")) {
                        com = creator.create(finalUserCommand[0]);
                        sender.send(com);
                        check();
                        break;
                    } else if (finalUserCommand[0].equals("update")) {
                        try {
                            int id = Integer.parseInt(finalUserCommand[1].trim());
                            com = creator.create(finalUserCommand[0], id);
                            sender.send(com);
                            check();
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("> Input error (id have to be an integer)");
                        }
                    } else if (finalUserCommand[0].equals("remove_by_id")) {
                        try {
                            int i = Integer.parseInt(finalUserCommand[1].trim());
                            com = creator.create(finalUserCommand[0], i);
                            sender.send(com);
                            check();
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("> Input error (id have to be an integer)");
                        }
                    } else if (finalUserCommand[0].equals("filter_contains_name")) {
                        com = creator.create(finalUserCommand[0], finalUserCommand[1].trim());
                        sender.send(com);
                        check();
                        break;
                    } else if (finalUserCommand[0].equals("execute_script")){
                        script(finalUserCommand[1].trim());
                        break;
                    }
                    else System.out.println("> Unidentified command - input 'help' for reference");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("> Missing argument");
                }
            }
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            System.exit(0);
        }
        com = null;
    }

    private void check(){
        try {
            receiver.receive().getMess().forEach(System.out::println);
        }catch (NullPointerException | PortUnreachableException e) {
            try {
                for (int i = 15; i > 0; i--) {
                    if (sender.Connection().getConnection()) {
                        sender.send(com);
                        receiver.receive().getMess().forEach(System.out::println);
                        break;
                    } else {
                        Thread.sleep(2000);
                        if (i==1) System.out.println("> Server isn't responding");
                    }
                }
            } catch (PortUnreachableException v) {
                System.out.println("> Port is unreachable");
            } catch (InterruptedException | NullPointerException v) {
                System.out.println("> Smth went wrong");
            }
        }
    }
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
                    if (j>0) sender.Connection().getConnection();
                    if (script.get(j)[0].equals("help") || script.get(j)[0].equals("info") ||
                            script.get(j)[0].equals("show") || script.get(j)[0].equals("add") ||
                            script.get(j)[0].equals("clear") ||
                            script.get(j)[0].equals("group_counting_by_from") ||
                            script.get(j)[0].equals("print_unique_distance") ||
                            script.get(j)[0].equals("remove_head") || script.get(j)[0].equals("add_if_min") ||
                            script.get(j)[0].equals("history")) {
                        sender.send(creator.create(script.get(j)[0], j, script));
                        receiver.receive().getMess().forEach(System.out::println);
                        if (script.get(j)[0].equals("exit")) System.exit(0);
                    }
                    else if (script.get(j)[0].equals("update")){
                        try {
                            int id = Integer.parseInt(script.get(j)[1].trim());
                            sender.send(creator.create(script.get(j)[0], j, script));
                            receiver.receive().getMess().forEach(System.out::println);
                        } catch (NumberFormatException e) {
                            System.out.println("> Input error (id have to be an integer)");
                            return;
                        }
                    }
                    else if (script.get(j)[0].equals("remove_by_id"))
                        try {
                            int k = Integer.parseInt(script.get(j)[1].trim());
                            sender.send(creator.create(script.get(j)[0], k));
                            receiver.receive().getMess().forEach(System.out::println);
                        } catch (NumberFormatException e) {
                            System.out.println("> Input error (id have to be an integer)");
                            return;
                        }
                    else if (script.get(j)[0].equals("execute_script")) script(script.get(j)[1].trim());
                    else if (script.get(j)[0].equals("filter_contains_name")) {
                        sender.send(creator.create(script.get(j)[0], script.get(j)[1].trim()));
                        receiver.receive().getMess().forEach(System.out::println);
                    }
                    else System.out.println("> Unidentified command");
                    if (script.get(j)[0].equals("add") || script.get(j)[0].equals("add_if_min") ||
                            script.get(j)[0].equals("update")) j+=10;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("> Missing argument, script is'n fully executed");
                    return;
                }catch (NumberFormatException | InputMismatchException e) {
                    System.out.println("> Wrong format, script is'n fully executed, check your file\n" +
                            "> \u001B[32mReference:\u001B[0m\n\u001B[31mfraction :\u001B[0m distance, " +
                            "x coordinate \u001B[31m(have to be more than -808)\u001B[0m," +
                            " coordinates of locations(from/to)\n\u001B[31minteger :\u001B[0m y coordinate\n" +
                            "\u001B[31mstring (not null) :\u001B[0m route's name (not empty), " +
                            "locations'(from/to) names\n" +
                            "(if you use id like an argument - it \u001B[31mhave to be an integer\u001B[0m)");
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("> File (script) not found");
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (PortUnreachableException e){
            System.out.println("> Port is unreachable");
        }
        rec.clear();
    }
}
