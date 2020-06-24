package Common;

import Collection.*;
import DataBase.Base;
import DataBase.WorkBase;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class ExecuteManager {
    private Base base = Base.getInstance();
    //массив команд и их описаний
    private String[][] commands;
    //хранение истории (названий команд)
    private ArrayDeque<String> history;
    //сообщение для передачи клиенту
    private ArrayDeque<String> mess;
    //"объект-одиночка"
    private static ExecuteManager executeManager;
    //подготовленная строка для запроса
    private String prState;
    private ReentrantLock lock = new ReentrantLock();

    {
        prState = "INSERT INTO ROUTES " +
                "(DISTANCE, ROUTE_NAME, X_COORD, Y_COORD, LOC_FROM_NAME, " +
                "X_COORD_FROM, Y_COORD_FROM, LOC_TO_NAME, X_COORD_TO, Y_COORD_TO, OWNER) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        history = new ArrayDeque<>();
        commands = new String[18][1];
        mess = new ArrayDeque<>();
        //сохранение коллекции в файл при отключении jvm
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                base.conDatabase().close();
            } catch (SQLException e) {
                System.out.println("Connection didn't close");
            }
        }));
    }

    public ExecuteManager(){
        read();
    }

    //инициализация/получение "объекта-одиночки"
    public static ExecuteManager getInstance() {
        if (executeManager == null) {
            executeManager = new ExecuteManager();
            return executeManager;
        } else return executeManager;
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
     * Вывод справки по доступным командам
     */
    public ArrayDeque<String> help() {
        lock.lock();
        for (int i = 0; i < 17; i++) {
            mess.add("> " + commands[i][0] + " : " + commands[i][1]);
        }
        lock.unlock();
        return mess;
    }

    /**
     * Вывод информации о таблице
     */
    public ArrayDeque<String> info() {
        try(Statement st = base.conDatabase().createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(*) FROM ROUTES")) {
            lock.lock();
            if(res.next()) {
                if (res.getInt("count")!=0) mess.add("Table's name: routes,\n" +
                        "Number of elements: " + res.getInt("count"));
                else mess.add("Table's name: routes,\n" +
                        "Table is empty");
            }
        }catch (SQLException e){
            mess.add("> Can't get data");
        }
        lock.unlock();
        return mess;
    }

    /**
     * Вывод элементов коллекции в строковом представлении
     */
    public ArrayDeque<String> show() {
        lock.lock();
        WorkBase.getRoutes().forEach((p) -> mess.add(p.toString()));
        lock.unlock();
        return mess;
    }

    //заполнение подготовленного запроса
    private int stateSet(PreparedStatement p_st, Route route) throws SQLException{
        p_st.setDouble(1, route.getDistance());
        p_st.setString(2, route.getName());
        p_st.setDouble(3, route.getCoordinates().getX());
        p_st.setInt(4, route.getCoordinates().getY());
        p_st.setString(5, route.getFrom().getName());
        p_st.setDouble(6, route.getFrom().getX());
        p_st.setFloat(7, route.getFrom().getY());
        p_st.setString(8, route.getTo().getName());
        p_st.setDouble(9, route.getTo().getX());
        p_st.setFloat(10, route.getTo().getY());
        p_st.setString(11, route.getOwnerLogin());
        return p_st.executeUpdate();
    }

    /**
     * Сортировка коллекции
     */
    private void sort() {
        ArrayDeque<Route> routes_2 = new ArrayDeque<>();
        WorkBase.getRoutes().stream().sorted().forEachOrdered(r -> routes_2.add(r));
        WorkBase.setRoutes(routes_2);
    }

    /**
     * Добавление нового элемента в коллекцию
     */
    public ArrayDeque<String> add(Route route) {
        try(PreparedStatement p_st = base.conDatabase().prepareStatement(prState)) {
            lock.lock();
            if(stateSet(p_st, route)!=0) {
                mess.add("> Element added");
                WorkBase.getRoutes().add(route);
                sort();
            }
        }catch (SQLException e){
            mess.add("> Element can't be added, database error");
        }
        lock.unlock();
        return mess;
    }

    /**
     * Обновление значения элемента
     */
    public ArrayDeque<String> update(Route route) {
        try(Statement st = base.conDatabase().createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(*) FROM ROUTES WHERE ID="
                    + route.getId()); PreparedStatement p_st = base.conDatabase().prepareStatement("" +
                "UPDATE ROUTES SET DISTANCE = ?, ROUTE_NAME = ?, X_COORD = ?, Y_COORD = ?," +
                " LOC_FROM_NAME = ?, X_COORD_FROM = ?, Y_COORD_FROM = ?, LOC_TO_NAME = ?, " +
                "X_COORD_TO = ?, Y_COORD_TO = ?, OWNER = ? WHERE ID=" + route.getId())){
            lock.lock();
            if (res.next()) {
                if(res.getInt("count")!=0){
                    if(stateSet(p_st, route)!=0) {
                        mess.add("> Element updated");
                        updateCollection(route);
                    }else throw new SQLException();
                }else mess.add("> Element with given id doesn't exist");
            }
        }catch (SQLException e){
            e.printStackTrace();
            mess.add("> Can't be updated, database error");
        }
        lock.unlock();
        return mess;
    }

    private void updateCollection(Route element) {
        ArrayDeque<Route> buf = new ArrayDeque<>();
        if (WorkBase.getRoutes().stream().anyMatch(r -> r.getId() == element.getId())) {
            for (Route route : WorkBase.getRoutes()) {
                if (route.getId() != element.getId()) {
                    buf.add(route);
                } else {
                    buf.add(element);
                }
            }
            WorkBase.setRoutes(buf);
            sort();
        }
    }

    /**
     * Удаление элемента из коллекции (по заданному id)
     * @param id - идентификационный номер
     */
    public ArrayDeque<String> remove_by_id(int id) {
        try(Statement st = base.conDatabase().createStatement()) {
            lock.lock();
            int res = st.executeUpdate("DELETE FROM ROUTES " +
                    "WHERE ID=" + id);
            if (res!=0) {
                for (Route route : WorkBase.getRoutes()) {
                    if (route.getId() == id) {
                        WorkBase.getRoutes().remove(route);
                        break;
                    }
                }
                mess.add("> Element is removed");
            }
            else mess.add("> Element with given id does not exist");
        }catch (SQLException e){
            mess.add("> Can't be removed");
        }
        lock.unlock();
        return mess;
    }


    /**
     * Очистка коллекции
     */
    public ArrayDeque<String> clear() {
        try(Statement st = base.conDatabase().createStatement()){
            lock.lock();
            int res = st.executeUpdate("DELETE * FROM ROUTES");
            if(res!=0){
                WorkBase.getRoutes().clear();
                mess.add("> Table is cleared");
            }else mess.add("Table has been already empty");

        }catch (SQLException e){
            mess.add("> Can't be cleared, database error");
        }
        lock.unlock();
        return mess;
    }

    /**
     * Завершение программы
     */
    public ArrayDeque<String> exit() {
        lock.lock();
        history.clear();
        mess.add("> Completion of work...");
        lock.unlock();
        return mess;
    }

    /**
     * Удаление первого элемента коллекции
     */
    public ArrayDeque<String> remove_head() {
        try(Statement st = base.conDatabase().createStatement();
        ResultSet res = st.executeQuery("SELECT MIN(ID) FROM ROUTES")) {
            lock.lock();
            int c = st.executeUpdate("DELETE FROM ROUTES WHERE ID=" + res.getInt("min"));
            if (c!=0){
                if (WorkBase.getRoutes().isEmpty()) throw new NoSuchElementException();
                WorkBase.getRoutes().removeFirst();
                mess.add("> Element is removed");
            }
            else mess.add("> Element with given id does not exist");
        } catch (SQLException e) {
            mess.add("Can't be removed");
        }
        lock.unlock();
        return mess;
    }


    /**
     * Добавление нового элемента в коллекцию, если его значение меньше,
     * чем у наименьшего элемента этой коллекции
     */
    public ArrayDeque<String> add_if_min(Route route) {
        lock.lock();
        try (Statement st_1 = base.conDatabase().createStatement();
             Statement st_2 = base.conDatabase().createStatement();
             Statement st_3 = base.conDatabase().createStatement();
             ResultSet count = st_1.executeQuery("SELECT COUNT(*) FROM ROUTES");
             ResultSet res = st_3.executeQuery("SELECT MIN(ID) FROM ROUTES");
             ResultSet first = st_2.executeQuery("SELECT * FROM ROUTES WHERE ID=" + res.getInt("min"));
             PreparedStatement p_st = base.conDatabase().prepareStatement(prState)){
            if(count.next()) {
                if (count.getInt("count") == 0) throw new NoSuchElementException();
            }
            if (first.next()) {
                if (route.compareBase(first.getString("ROUTE_NAME"),
                        first.getDouble("DISTANCE")) < 0) {
                    if (stateSet(p_st, route) != 0) {
                        WorkBase.getRoutes().add(route);
                        sort();
                        mess.add("> Element added");
                    }
                } else mess.add("> Element isn't minimal");
            }
        } catch (SQLException e) {
            mess.add("> Can't be added, database error");
            e.printStackTrace();
        }catch (NoSuchElementException e){
            mess.add("> Collection is empty, element haven't been added");
        }
        lock.unlock();
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
        lock.lock();
        if (history.size() < 11) history.add(string);
        else {
            history.pop();
            history.add(string);
        }
        lock.unlock();
    }

    /**
     * Группировака элементов коллекции по значению поля from, вывод количества элементов в каждой группе
     */
    public ArrayDeque<String> group_counting_by_from() {
        lock.lock();
        //подсчет количества уникальных значений поля from
        ArrayList<String> fr = new ArrayList<>();
        WorkBase.getRoutes().stream().map(route -> route.getFrom().getName()).distinct().forEachOrdered(k -> fr.add(k));
        //подсчет элементов в каждой группе
        int t = 0;
        for (int i = 0; i < fr.size(); i++) {
            for (Route route : WorkBase.getRoutes()) {
                if (route.getFrom().getName().equals(fr.get(i))) t++;
            }
            mess.add(fr.get(i) + " [" + t + "]");
            t = 0;
        }
        lock.unlock();
        return mess;
    }

    /**
     * Вывод элементов, значение поля 'name' которых содержит заданную подстроку
     *
     * @param str - заданная подстрока
     */
    public ArrayDeque<String> filter_contains_name(String str){
        lock.lock();
        WorkBase.getRoutes().stream().filter(route -> route.getName().contains(str)).forEachOrdered(route -> mess.add(route.getName()));
        if (mess.isEmpty()) mess.add("> No matches found");
        lock.unlock();
        return mess;
    }

    /**
     * Вывод уникальных значений поля distance
     */
    public ArrayDeque<String> print_unique_distance() {
        lock.lock();
        WorkBase.getRoutes().stream().map(route -> route.getDistance()).distinct().forEachOrdered(k -> mess.add(k.toString()));
        lock.unlock();
        return mess;
    }
}

