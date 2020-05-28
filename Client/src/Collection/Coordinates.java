package Collection;
import java.io.Serializable;

/**
 * Класс хранит объекты, которые задают координаты местонахождения пользователя
 */

public class Coordinates implements Serializable {
    private double x;
    private Integer y;

    public Coordinates(double x, Integer y){
        this.x = x;
        this.y = y;
    }

    public double getX() {return this.x;}
    public Integer getY(){return this.y;}
}
