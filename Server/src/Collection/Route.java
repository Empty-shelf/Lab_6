package Collection;

import java.io.Serializable;

/**
 * Класс, коллекцией экземпляров которого управляет программа
 */
public class Route implements Comparable<Route>, Serializable {

    private int id;
    private String name;
    private Coordinates coordinates;
    private Location from;
    private Location to;
    private double distance;
    private String ownerLogin;

    public String getOwnerLogin() {
        return ownerLogin;
    }
    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }
    public void setId(int id) {this.id = id; }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return this.coordinates;
    }
    public Location getFrom() {
        return this.from;
    }
    public Location getTo() {return this.to;}
    public double getDistance() {return this.distance;}

    public Route(double distance, String name, Coordinates coordinates, Location from, Location to, String login) {
        this.coordinates = coordinates;
        this.ownerLogin = login;
        this.distance = distance;
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public int compareBase(String name, double distance) {
        int result = this.name.compareTo(name);
        if (result == 0){
            if (this.distance - distance > 0) return 1;
            else if(this.distance - distance < 0) return -1;
            else return 0;
        }
        else return result;
    }

    @Override public int compareTo(Route route) {
        int result = this.name.compareTo(route.name);
        if (result == 0){
            if (this.distance - route.distance > 0) return 1;
            else if(this.distance - route.distance < 0) return -1;
            else return 0;
        }
        else return result;
    }

    @Override
    public String toString() {
        return "Id: " + this.getId() + "\nName: " + this.getName() + "\nCoordinates {x:" +
                coordinates.getX() + ", y:" + coordinates.getY() + "}\n" + "Distance: " + this.getDistance() +
                "\nLocation {from:" + this.getFrom().getName() + ", to:" + this.getTo().getName() + "}";
    }
}