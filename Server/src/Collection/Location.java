package Collection;

import java.io.Serializable;

/**
 * Класс хранит объекты, которые задают начальныю и конечную точку пути
 */
public class Location implements Serializable {
    private String name;
    private double x;
    private float y;

    public double getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Location(String name, double x, float y) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
