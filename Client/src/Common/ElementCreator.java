package Common;

import Collection.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ElementCreator {

    Route constructorSystemIn(int id) {
        while (true) {
            try {
                Scanner field = new Scanner(System.in);
                System.out.println("> Input distance:");
                double distance = field.nextDouble();
                System.out.println("> Input route's name:");
                if (field.nextLine() == null) throw new InputMismatchException();
                String name = field.nextLine();
                if (name.trim().length()==0){
                    System.out.println("> Empty string entered");
                    throw new InputMismatchException();
                }
                System.out.println("> Input x coordinate:");
                double x = field.nextDouble();
                if (x <= -808) throw new InputMismatchException();
                System.out.println("> Input y coordinate:");
                Integer y = field.nextInt();
                System.out.println("> Input name of location from:");
                if (field.nextLine() == null) throw new InputMismatchException();
                String locFrom = field.nextLine();
                System.out.println("> Input x coordinate of location from:");
                double fromX = field.nextDouble();
                System.out.println("> Input y coordinate of location from:");
                float fromY = field.nextFloat();
                System.out.println("> Input name of location to:");
                if (field.nextLine() == null) throw new InputMismatchException();
                String locTo = field.nextLine();
                System.out.println("> Input x coordinate of location to:");
                double toX = field.nextDouble();
                System.out.println("> Input y coordinate of location from:");
                float toY = field.nextFloat();
                Coordinates coordinates = new Coordinates(x, y);
                Location locationFrom = new Location(locFrom, fromX, fromY);
                Location locationTo = new Location(locTo, toX, toY);
                if (id != 0) {
                    Route route =  new Route(distance, name, coordinates, locationFrom, locationTo);
                    route.setId(id);
                    return route;
                } else return new Route(distance, name, coordinates, locationFrom, locationTo);
            }catch (InputMismatchException e){
                System.out.println("> Input error\n\u001B[34mReference:\u001B[0m\n\u001B[31mfraction :\u001B[0m" +
                        " distance, x coordinate \u001B[31m(have to be more than -808)\u001B[0m," +
                        " coordinates of locations(from/to)\n\u001B[31minteger :\u001B[0m y coordinate\n"+
                        "\u001B[31mstring (not null) :\u001B[0m route's name (not empty), locations'(from/to) names");
            }
        }
    }

    /**
     * Метод для создания элемента коллекции (использование данных скрипта)
     * @param j - номер элемента коллекции
     * @param script - коллекция-скрипт
     * @return объект класса Route
     */
    Route constructorFile(int j, ArrayList<String[]> script) {
        double distance = Double.parseDouble(script.get(j + 1)[0]);
        String name = script.get(j + 2)[0];
        try {
            if (name.trim().length()==0) {
                System.out.println("> Empty string entered");
                throw new InputMismatchException();
            }
        }catch (InputMismatchException e){
            System.out.println("> Wrong format\n" +
                    "\u001B[31mstring (not null) :\u001B[0m route's name (not empty)");
            System.exit(1);
        }
        double x = Double.parseDouble(script.get(j + 3)[0]);
        Integer y = Integer.valueOf(script.get(j + 4)[0]);
        String locFrom = script.get(j + 5)[0];
        double fromX = Double.parseDouble(script.get(j + 6)[0]);
        float fromY = Float.parseFloat(script.get(j + 7)[0]);
        String locTo = script.get(j + 8)[0];
        double toX = Double.parseDouble(script.get(j + 9)[0]);
        float toY = Float.parseFloat(script.get(j + 10)[0]);
        Coordinates coordinates = new Coordinates(x, y);
        Location locationFrom = new Location(locFrom, fromX, fromY);
        Location locationTo = new Location(locTo, toX, toY);
        if (script.get(j)[0].equals("update")) {
            Route route =  new Route(distance, name, coordinates, locationFrom, locationTo);
            route.setId(Integer.parseInt(script.get(j)[1]));
            return route;
        } else return new Route(distance, name, coordinates, locationFrom, locationTo);
    }
}
