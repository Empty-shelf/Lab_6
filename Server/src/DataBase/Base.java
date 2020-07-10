package DataBase;

import java.sql.*;

public class Base{
        private static String url = "jdbc:postgresql://localhost:5432/Lab_7";
        private static String login = "postgres";
        private static String password = "postgres";
        //объект-одиночка
        private static Base base;
        private static Connection con;

    static {
        try {
            //загрузка драйвера
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver problem");
        }
    }
    //возвращает единственный объект класса
    public static Base getInstance(){
        if (base == null){
            base = new Base();
            return base;
        }
        else return base;
    }
    //возвращает объект абстракции соединения
    public static Connection conDatabase(){
        if (con == null) {
            try {
                con = DriverManager.getConnection(url, login, password);
                return con;
            } catch (SQLException e) {
                System.out.println("");
                return null;
            }
        }else return con;
    }
}
