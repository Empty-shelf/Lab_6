package Common;


import Collection.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;


public class ExecuteManager {
    private ArrayDeque<Route> ways; //коллекция
    private Date dateOfCreation;
    private String[][] commands; //массив команд и их описаний
    private File csvFile;
    private ArrayDeque<String> history;  //история

    private ArrayDeque<String> mess;

    private static ExecuteManager executeManager;

    {
        ways = new ArrayDeque<>();
        history = new ArrayDeque<>();
        commands = new String[16][1];
        mess = new ArrayDeque<>();
    }


    private ExecuteManager(String collPath) {
        try {
            if (collPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            System.out.println("> File not found (environment variable is empty)");
            System.exit(1);
        }
        File file = new File(collPath);
        try {
            if (file.exists()) {
                this.csvFile = file;
                load();
            } else throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            System.out.println("> File not found");
            System.exit(1);
        }
        dateOfCreation = new Date();
        read();
    }


    public static ExecuteManager getInstance(String filepath){
        if(executeManager ==null){
           executeManager = new  ExecuteManager(filepath);
           return executeManager;
        }
        else return executeManager;
    }

    /**
     * Чтение файла, добавление элементов в коллекцию
     */
    private void load() {
        boolean flag = true;
        try {
            if (!csvFile.canRead() || !csvFile.canWrite()) throw new SecurityException();
        } catch (SecurityException e) {
            System.out.println("> File access denied");
            System.exit(1);
        }
        if (csvFile.length() == 0) {
            System.out.println("> File is empty");
            return;
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile)))) {
            while (flag) {
                String[] fields;
                String s = in.readLine();
                if (s != null) {
                    fields = s.trim().split(",", 10);
                    double distance = Double.parseDouble(fields[0]);
                    String name = fields[1];
                    if (name.trim().length() == 0) {
                        System.out.println("> Empty string entered");
                        throw new IOException();
                    }
                    double x = Double.parseDouble(fields[2]);
                    Integer y = Integer.valueOf(fields[3]);
                    String nameLocationFrom = fields[4];
                    double fromX = Double.parseDouble(fields[5]);
                    float fromY = Float.parseFloat(fields[6]);
                    String nameLocationTo = fields[7];
                    double ToX = Double.parseDouble(fields[8]);
                    float ToY = Float.parseFloat(fields[9]);
                    Coordinates coordinates = new Coordinates(x, y);
                    Location from = new Location(nameLocationFrom, fromX, fromY);
                    Location to = new Location(nameLocationTo, ToX, ToY);
                    Route route = new Route(distance, name, coordinates, from, to);
                    ways.add(route);
                } else flag = false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("> File with data not found");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("> Invalid argument format in file");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("> Input error");
            System.exit(1);
        }
        sort();
        System.out.println("> Collection uploaded");
    }

    /**
     * Чтение файла с набором доступных комманд и их описанием в двумерный массив
     */
    private void read() {
        try (Scanner in = new Scanner(new File("file_commands.txt"))) {
            String s;
            int i = 0;
            while (in.hasNextLine()) {
                s = in.nextLine();
                commands[i] = s.split(":");
                i++;
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("> File (with commands) not found");
            System.exit(1);
        }
    }

    /**
     * Очистка хранилища сообщения для клиента
     */
    public void clearMess() {
        mess.clear();
    }

    /**
     * Сортировка коллекции
     */
    private void sort() {
        ArrayDeque<Route> ways2 = new ArrayDeque<>();
        ways.stream().sorted().forEachOrdered(r -> ways2.add(r));
        ways = ways2;
    }

    /**
     * Вывод справки по доступным командам
     */
    public ArrayDeque<String> help() {
        for (int i = 0; i < 15; i++) {
            mess.add("> " + commands[i][0] + " : " + commands[i][1]);
        }
        return mess;
    }

    /**
     * Вывод информации о коллекции
     */
    public ArrayDeque<String> info() {
        mess.add("Collection type: " + ways.getClass().getName() + ",\nCreation date: " +
                dateOfCreation + ",\nAmount of elements: " + ways.size());
        return mess;
    }

    /**
     * Вывод элементов коллекции в строковом представлении
     */
    public ArrayDeque<String> show() {
        ways.stream().forEachOrdered((p) -> mess.add(p.toString()));
        return mess;
    }

    /**
     * Добавление нового элемента в коллекцию
     */
    public ArrayDeque<String> add(Route route) {
        ways.add(route);
        sort();
        mess.add("> Element added");
        return mess;
    }

    /**
     * Обновление значения элемента коллекции
     */
    public ArrayDeque<String> update(Route element) {
        ArrayDeque<Route> buf = new ArrayDeque<>();
        if (ways.stream().filter(r -> r.getId() == element.getId()) != null) {
            for (Route route : ways) {
                if (route.getId() != element.getId()) {
                    buf.add(route);
                } else {
                    buf.add(element);
                }
            }
            ways = buf;
            sort();
            mess.add("> Element updated");
        } else mess.add("> Element with given id doesn't exist");
        return mess;
    }

    /**
     * Удаление элемента из коллекции (по заданному id)
     *
     * @param id - идентификационный номер
     */
    public ArrayDeque<String> remove_by_id(int id) {
        Route route = ways.stream().filter(r -> r.getId() == id).findAny().get();
        if (ways.remove(route)) {
            mess.add("> Element removed");
        } else mess.add("> Element with given id does not exist");
        return mess;
    }

    /**
     * Очистка коллекции
     */
    public ArrayDeque<String> clear() {
        ways.clear();
        mess.add("> Collection cleared");
        return mess;
    }

    /**
     * Возвращение строкового представления элемента в формате csv
     *
     * @param route - объект класса Route
     * @return String
     */
    private String write(Route route) {
        return route.getDistance() + "," + route.getName() + "," +
                route.getCoordinates().getX() + "," + route.getCoordinates().getY() +
                "," + route.getFrom().getName() + "," + route.getFrom().getX() + "," +
                route.getFrom().getY() + "," + route.getTo().getName() + "," + route.getTo().getX() +
                "," + route.getTo().getY();
    }

    /**
     * Сохранение коллекции в файл
     */
    private void save() {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(csvFile))) {
            for (Route route : ways) {
                out.println(write(route));
            }
        } catch (FileNotFoundException e) {
            System.out.println("> File not found");
        }
        System.out.println("> Collection saved");
    }

    /**
     * Завершение программы с сохранением коллекции
     */
    public ArrayDeque<String> exit() {
        save();
        mess.add("> Completion of work...");
        return mess;
    }

    /**
     * Вывод и удаление первого элемента коллекции
     */
    public ArrayDeque<String> remove_head() {
        try {
            if (ways.isEmpty()) throw new NoSuchElementException();
            ways.removeFirst();
            mess.add("> Element removed");
        } catch (NoSuchElementException e) {
            mess.add("> Collection is empty");
        }
        return mess;
    }

    /**
     * Добавление нового элемента в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     */
    public ArrayDeque<String> add_if_min(Route route) {
        try {
            if (ways.isEmpty()) throw new NoSuchElementException();
            if (ways.peekFirst().compareTo(route) > 0) {
                ways.addFirst(route);
                mess.add("> Element added");
            } else mess.add("> Element not minimal");
        } catch (NoSuchElementException e) {
            mess.add("> Collection is empty, element didn't add");
        }
        return mess;
    }

    /**
     * Вывод последних 11 команд (история)
     */
    public ArrayDeque<String> history() {
        return history;
    }

    /**
     * Создание истории вызова команд (11)
     *
     * @param string - имя команды
     */
    public void addToHistory(String string) {
        if (history.size() < 11) history.add(string);
        else {
            history.pop();
            history.add(string);
        }
    }

    /**
     * Группировака элементов коллекции по значению поля from, вывод количества элементов в каждой группе
     */
    public ArrayDeque<String> group_counting_by_from() {
        //подсчет количества уникальных значений поля from
        ArrayList<String> fr = new ArrayList<>();
        ways.stream().map(route -> route.getFrom().getName()).distinct().forEachOrdered(k -> fr.add(k));
        //подсчет элементов в каждой группе
        int t = 0;
        for (int i = 0; i < fr.size(); i++) {
            for (Route route : ways) {
                if (route.getFrom().getName().equals(fr.get(i))) t++;
            }
            mess.add(fr.get(i) + " [" + t + "]");
            t = 0;
        }
        return mess;
    }

    /**
     * Вывод элементов, значение поля 'name' которых содержит заданную подстроку
     *
     * @param str - заданная подстрока
     */
    public ArrayDeque<String> filter_contains_name(String str) {
        ways.stream().filter(route -> route.getName().contains(str)).forEachOrdered(route -> mess.add(route.getName()));
        if (mess.isEmpty()) mess.add("> No matches found");
        return mess;
    }

    /**
     * Вывод уникальных значений поля distance
     */
    public ArrayDeque<String> print_unique_distance() {
        ways.stream().map(route -> route.getDistance()).distinct().forEachOrdered(k -> mess.add(k.toString()));
        return mess;
    }
}

