package Common;

import static Work.ClientMain.sender;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Commander {

    private String userCommand;
    private String[] finalUserCommand;
    private ArrayList<String> rec;

    private CommandCreator creator = new CommandCreator();

    public void interactiveMod() {
        System.out.println("> Ready for work");
        try (Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand.equals("exit")) {
                userCommand = commandReader.nextLine();
                if (userCommand == null) throw new NoSuchElementException();
                finalUserCommand = userCommand.trim().split(" ", 2);
                if (finalUserCommand[0].equals("help") || finalUserCommand[0].equals("info") ||
                        finalUserCommand[0].equals("show") || finalUserCommand[0].equals("add") ||
                        finalUserCommand[0].equals("clear") || finalUserCommand[0].equals("exit") ||
                        finalUserCommand[0].equals("group_counting_by_from") ||
                        finalUserCommand[0].equals("print_unique_distance") ||
                        finalUserCommand[0].equals("remove_head") || finalUserCommand[0].equals("add_if_min") ||
                        finalUserCommand[0].equals("history")) sender.send(creator.create(finalUserCommand[0]));
                else if (finalUserCommand[0].equals("update")) {
                    try {
                        int id = Integer.parseInt(finalUserCommand[1].trim());
                        sender.send(creator.create(finalUserCommand[0], id));
                    } catch (NumberFormatException e) {
                        System.out.println("> Input error (id have to be an integer)");
                    }
                }
                else if ( finalUserCommand[0].equals("remove_by_id")) {
                    try {
                        int i = Integer.parseInt(finalUserCommand[1].trim());
                        sender.send(creator.create(finalUserCommand[0], i));
                    } catch (NumberFormatException e) {
                        System.out.println("> Input error (id have to be an integer)");
                    }
                }
                else if (finalUserCommand[0].equals("filter_contains_name")) {
                    sender.send(creator.create(finalUserCommand[0], finalUserCommand[1].trim()));
                }
                else if (finalUserCommand[0].equals("execute_script")) script(finalUserCommand[1].trim());
                else System.out.println("> Unidentified command - input 'help' for reference");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("> Missing argument");
        } catch (NoSuchElementException e) {
            System.out.println("> No line found");
        }

    }

    public void script(String file_path){
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
                    if (script.get(j)[0].equals("help") || script.get(j)[0].equals("info") ||
                            script.get(j)[0].equals("show") || script.get(j)[0].equals("add") ||
                            script.get(j)[0].equals("clear") || script.get(j)[0].equals("exit") ||
                            script.get(j)[0].equals("group_counting_by_from") ||
                            script.get(j)[0].equals("print_unique_distance") ||
                            script.get(j)[0].equals("remove_head") || script.get(j)[0].equals("add_if_min") ||
                            script.get(j)[0].equals("history"))
                        sender.send(creator.create(script.get(j)[0], j, script));
                    else if (script.get(j)[0].equals("update")){
                        try {
                            int id = Integer.parseInt(script.get(j)[1].trim());
                            sender.send(creator.create(finalUserCommand[0], j, script));
                        } catch (NumberFormatException e) {
                            System.out.println("> Input error (id have to be an integer)");
                        }
                    }
                    else if (script.get(j)[0].equals("remove_by_id"))
                         try {
                             int k = Integer.parseInt(script.get(j)[1].trim());
                             sender.send(creator.create(script.get(j)[0], k));
                         } catch (NumberFormatException e) {
                             System.out.println("> Input error (id have to be an integer)");
                         }

                    else if (script.get(j)[0].equals("execute_script")) script(finalUserCommand[1].trim());
                    else if (script.get(j)[0].equals("filter_contains_name"))
                        sender.send(creator.create(finalUserCommand[0], finalUserCommand[1].trim()));
                    else System.out.println("> Unidentified command");
                    if (script.get(j)[0].equals("add") || script.get(j)[0].equals("add_if_min") ||
                            script.get(j)[0].equals("update")) j+=10;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("> Missing argument");
                    rec.clear();
                    System.exit(1);
                }catch (NumberFormatException e) {
                    System.out.println("> Wrong format, check your file\n" +
                            "> \u001B[32mReference:\u001B[0m\n\u001B[31mfraction :\u001B[0m distance, " +
                            "x coordinate \u001B[31m(have to be more than -808)\u001B[0m," +
                            " coordinates of locations(from/to)\n\u001B[31minteger :\u001B[0m y coordinate\n" +
                            "\u001B[31mstring (not null) :\u001B[0m route's name (not empty), " +
                            "locations'(from/to) names\n" +
                            "(if you use id like an argument - it \u001B[31mhave to be an integer\u001B[0m)");
                    rec.clear();
                    System.exit(1);
                }
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("> File (script) not found");
            rec.clear();
        }
        rec.clear();
    }
}

