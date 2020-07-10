package Common;

import Collection.*;
import UI.AnswerWindow;
import UI.InfoWindow;
import UI.MultiInputWindow;
import org.omg.CORBA.UnknownUserException;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//создание аргумента, который является элементом коллекции
class ElementCreator {
    private Route route;

    public Route getRoute() {
        try {
            return route;
        }finally {
            route = null;
        }
    }

    void constructor(int id, String login, MultiInputWindow window) throws IllegalArgumentException{
        try {
            double distance = Double.parseDouble(window.getDistance());
            if (distance < 2) {
                InfoWindow w = new InfoWindow(" Distance have to be more than 1 ", "error");
                return;
            }
            String name = window.getName();
            if (name.trim().length() == 0) {
                InfoWindow w = new InfoWindow(" Route's name can't be empty ", "error");
                return;
            }
            double x = Double.parseDouble(window.getCoord_x());
            if (x <= -808) {
                InfoWindow w = new InfoWindow(" X coordinate have to be more than -808 ", "error");
                return;
            }
            Integer y = Integer.valueOf(window.getCoord_y());
            String locFrom = window.getLoc_from();
            double fromX = Double.parseDouble(window.getFrom_x());
            float fromY = Float.parseFloat(window.getFrom_y());
            String locTo = window.getLoc_to();
            double toX = Double.parseDouble(window.getTo_x());
            float toY = Float.parseFloat(window.getTo_y());
            Coordinates coordinates = new Coordinates(x, y);
            Location locationFrom = new Location(locFrom, fromX, fromY);
            Location locationTo = new Location(locTo, toX, toY);
            if (id != 0) {
                route = new Route(distance, name, coordinates, locationFrom, locationTo, login);
                route.setId(id);
            } else route = new Route(distance, name, coordinates, locationFrom, locationTo, login);
        }catch (InputMismatchException | NumberFormatException er){
            InfoWindow w = new InfoWindow(" Input error, check it ", "error");
        }
    }


    /**
     * Метод для создания элемента коллекции (использование данных скрипта)
     * @param j - номер элемента коллекции
     * @param script - коллекция-скрипт
     * @return объект класса Route
     */
    Route constructor(int j, ArrayList<String[]> script, String login) throws InputMismatchException{
        double distance = Double.parseDouble(script.get(j + 1)[0]);
        if (distance < 2) throw new InputMismatchException();
        String name = script.get(j + 2)[0];
        if (name.trim().length() == 0) {
            System.out.println("> Empty string entered");
            throw new InputMismatchException();
        }
        double x = Double.parseDouble(script.get(j + 3)[0]);
        if (x <= -808) throw new InputMismatchException();
        Integer y = Integer.valueOf(script.get(j + 4)[0]);
        String locFrom = script.get(j + 5)[0];
        double fromX = Double.parseDouble(script.get(j + 6)[0]);
        float fromY = Float.parseFloat(script.get(j + 7)[0]);
        String locTo = script.get(j + 8)[0];
        double toX = Double.parseDouble(script.get(j + 9)[0]);
        float toY = Float.parseFloat(script.get(j + 10)[0]);
        String log = script.get(j + 11)[0].trim();
        if (log != login) log = login;
        Coordinates coordinates = new Coordinates(x, y);
        Location locationFrom = new Location(locFrom, fromX, fromY);
        Location locationTo = new Location(locTo, toX, toY);
        if (script.get(j)[0].equals("update")) {
            Route route = new Route(distance, name, coordinates, locationFrom, locationTo, log);
            route.setId(Integer.parseInt(script.get(j)[1]));
            return route;
        } else return new Route(distance, name, coordinates, locationFrom, locationTo, log);
    }
}