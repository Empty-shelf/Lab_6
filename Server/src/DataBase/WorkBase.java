package DataBase;

import Collection.Coordinates;
import Collection.Location;
import Collection.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;

public class WorkBase {
    private static ArrayDeque<Route> routes = new ArrayDeque<>();

    public static ArrayDeque<Route> getRoutes(){
        return routes;
    }
    public static void setRoutes(ArrayDeque<Route> r){routes = r;}

    //загрузка данных
    static {
        try(Statement st = Base.getInstance().conDatabase().createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM ROUTES")) {
            while (res.next()) {
                int id = res.getInt("ID");
                String name = res.getString("ROUTE_NAME");
                Coordinates coordinates = new Coordinates(res.getDouble("X_COORD"), res.getInt("Y_COORD"));
                Location from = new Location(res.getString("LOC_FROM_NAME"),
                        res.getDouble("X_COORD_FROM"), res.getFloat("Y_COORD_FROM"));
                Location to = new Location(res.getString("LOC_TO_NAME"),
                        res.getDouble("X_COORD_TO"), res.getFloat("Y_COORD_TO"));
                double distance = res.getDouble("DISTANCE");
                String ownerLogin = res.getString("OWNER");
                Route route = new Route(distance, name, coordinates, from, to, ownerLogin);
                route.setId(id);
                routes.add(route);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
