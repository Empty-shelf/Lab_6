package Common;

import Collection.*;
import Commands.Add;
import Commands.AddIfMin;

import java.io.*;
import java.util.*;

public class Manager {
    private ArrayDeque<Route> ways; //коллекция
    private Date dateOfCreation;
    private String[][] commands; //массив команд и их описаний
    private File csvFile;
    private ArrayDeque<String> history;  //история


    {
        ways = new ArrayDeque<>();
        history = new ArrayDeque<String>();
        commands = new String[16][1];
    }

    public Manager(String collPath){
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
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile)))){
            while (flag) {
                String[] fields;
                String s = in.readLine();
                if (s != null) {
                    fields = s.trim().split(",", 10);
                    double distance = Double.parseDouble(fields[0]);
                    String name = fields[1];
                    if (name.trim().length()==0) {
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
            System.out.println("> File not found");
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
    void read() {
        try (Scanner in = new Scanner(new File("file_commands.txt"))){
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
     * Сортировка коллекции
     */
    private void sort() {
        Route[] array = ways.toArray(new Route[0]);
        boolean flag = false;
        Route k;
        while (!flag) {
            flag = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i].compareTo(array[i + 1]) > 0) {
                    flag = false;
                    k = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = k;
                }
            }
        }
        ways = new ArrayDeque<Route>(Arrays.asList(array));
    }
    /**
     * Вывод справки по доступным командам
     */
    public void help() {
        for (int i = 0; i < 16; i++) {
            System.out.println("> " + commands[i][0] + " : " + commands[i][1]);
        }
    }

    /**
     * Вывод информации о коллекции
     */
    public void info() {
        System.out.println("Collection type: " + ways.getClass().getName() + ",\nCreation date: " +
                dateOfCreation + ",\nAmount of elements: " + ways.size());
    }

    /**
     * Вывод элементов коллекции в строковом представлении
     */
    public void show() {
        for (Route route : ways) {
            System.out.println(route);
        }
    }

    /**
     * Добавление нового элемента в коллекцию
     */
    public String add(Route route) {
        ways.add(route);
        sort();
        return "> Element added";
    }

    /**
     * Обновление значения элемента коллекции
     */
    public void update(Route element) {
        boolean flag = false;
        ArrayDeque<Route> buf = new ArrayDeque<>();
        for (Route route : ways) {
            if (route.getId() != element.getId()) {
                buf.add(route);
            } else {
                flag = true;
                buf.add(element);
            }
        }
        if (flag) {
            ways = buf;
            sort();
            System.out.println("> Element updated");
        } else {
            System.out.println("> Element with given id doesn't exist");
        }
    }

    /**
     * Удаление элемента из коллекции (по заданному id)
     * @param id - идентификационный номер
     */
    public void remove_by_id(int id) {
        boolean flag = false;
        for (Route route : ways) {
            if (route.getId() == id) {
                ways.remove(route);
                flag = true;
                System.out.println("> Element removed");
                break;
            }
        }
        if (!flag) System.out.println("> Element with given id does not exist");
    }

    /**
     * Очистка коллекции
     */
    public void clear() {
        ways.clear();
        System.out.println("> Collection cleared");
    }

    /**
     * Возвращение строкового представления элемента в формате csv
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
    void save() {
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
     * Завершение программы без сохранения в файл
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Вывод и удаление первого элемента коллекции
     */
    public void remove_head() {
        try {
            if (ways.isEmpty()) throw new NoSuchElementException();
            ways.removeFirst();
            System.out.println("> Element removed");
        } catch (NoSuchElementException e) {
            System.out.println("> Collection is empty");
        }
    }

    /**
     * Добавление нового элемента в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     */
    public String add_if_min(Route route) {
        try {
            if (ways.isEmpty()) throw new NoSuchElementException();
            if (ways.peekFirst().compareTo(route) > 0) {
                ways.addFirst(route);
                return "> Element added";
            }
            else return "> Not minimal";
        } catch (NoSuchElementException e) {
            return "> Collection is empty";
        }
    }

    /**
     * Вывод последних 11 команд (история)
     */
    public void history() {
        if (!history.isEmpty()) {
            for (String str : history) {
                System.out.println(str);
            }
        } else System.out.println("> History is empty");
    }

    /**
     * Создание истории вызова команд (11)
     * @param string - имя команды
     */
    void addToHistory(String string){
        if (history.size()<11) history.add(string);
        else {
            history.pop();
            history.add(string);
        }
    }

    /**
     * Группировака элементов коллекции по значению поля from, вывод количества элементов в каждой группе
     */
    public void group_counting_by_from() {
        //подсчет количества уникальных значений поля from
        boolean flag = true;
        ArrayList<Route> fr = new ArrayList<>();
        for (Route route : ways) {
            if (!fr.isEmpty()) {
                for (Route routeUn : fr) {
                    if (route.getFrom().getName().equals(routeUn.getFrom().getName())) {
                        flag = false;
                        break;
                    }
                }
            } else {
                fr.add(route);
                flag = false;
            }
            if (flag) fr.add(route);
            flag = true;
        }
        //подсчет элементов в каждой группе
        int t = 0;
        for (int i = 0; i < fr.size(); i++) {
            for (Route route : ways) {
                if (route.getFrom().getName().equals(fr.get(i).getFrom().getName())) t++;
            }
            System.out.println(fr.get(i).getFrom().getName() + " [" + t + "]");
            t = 0;
        }
    }

    /**
     * Вывод элементов, значение поля 'name' которых содержит заданную подстроку
     * @param name - заданная подстрока
     */
    public void filter_contains_name(String name) {
        boolean flag = true;
        for (Route route : ways){
            if (route.getName().contains(name)) {
                System.out.println(route);
                flag = false;
            }
        }
        if (flag) System.out.println("> No matches found");
    }

    /**
     * Вывод уникальных значений поля distance
     */
    public void print_unique_distance() {
        boolean flag = true;
        ArrayList<Route> dis = new ArrayList<>();
        for (Route route : ways) {
            if (!dis.isEmpty()) {
                for (Route routeUn : dis) {
                    if (route.getDistance() == routeUn.getDistance()) {
                        flag = false;
                        break;
                    }
                }
            } else {
                dis.add(route);
                flag = false;
            }
            if (flag) dis.add(route);
            flag = true;
        }
        for (Route route : dis) {
            System.out.println(route.getDistance());
        }
    }
}
