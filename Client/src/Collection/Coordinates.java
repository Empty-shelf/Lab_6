package Collection;

import java.io.Serializable;

/**
 * Класс хранит объекты, которые задают координаты местонахождения пользователя
 */
public class Coordinates implements Serializable {
    private double x; //Значение поля должно быть больше -808
    private Integer y; //Поле не может быть null

    public Coordinates(double x, Integer y){
        this.x = x;
        this.y = y;
    }

    public double getX() {return this.x;}
    public Integer getY(){return this.y;}

}
