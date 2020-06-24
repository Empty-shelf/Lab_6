package Collection;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private double x;
    private Integer y;

    public double getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }


    public Coordinates(double x, Integer y) {
        this.x = x;
        this.y = y;
    }

}
