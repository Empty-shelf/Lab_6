package DataBase;

import Collection.Coordinates;
import Collection.Location;
import Collection.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class WorkBase {
    private static ArrayList<Route> routes = new ArrayList<>();

    public static synchronized ArrayList<Route> getRoutes(){
        return routes;
    }
    public static synchronized void setRoutes(ArrayList<Route> r){routes = r;}

    //загрузка данных
    public static void load(){
        ArrayList<Route> routes_2 = new ArrayList<>();
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
                routes_2.add(route);
            }
            setRoutes(routes_2);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
