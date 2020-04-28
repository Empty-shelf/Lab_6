package Collection;

import java.io.Serializable;

/**
 * Класс хранит объекты, которые задают начальныю и конечную точку пути
 */
public class Location implements Serializable {
    private String name; //Поле не может быть null
    private double x;
    private float y;

    public Location(String name, double x, float y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName(){return this.name;}
    public double getX(){return this.x;}
    public float getY(){return this.y;}
}