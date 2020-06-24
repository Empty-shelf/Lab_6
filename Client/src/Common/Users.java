package Common;

import DataBase.Base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

public class Users {
    private static Hashtable<String, String> loggedUsers = new Hashtable<>();
    private static Base base = Base.getInstance();

    public static Hashtable getLoggedUsers(){
        return loggedUsers;
    }
    public static void addLoggedUser(String login, String password){
        loggedUsers.put(login, password);
    }
    public static void removeLoggedUser(String login){
        loggedUsers.remove(login);
    }

    public static boolean isRegistered(String login){
        try(Statement st = base.conDatabase().createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(*) FROM USERS WHERE LOGIN='" + login + "'")){
            int count;
            if (res.next()){
                count = res.getInt("count");
                if (count==0) return false;
                else return true;
            }else return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isCorrectPassword(String login, String password){
        try(Statement st = base.conDatabase().createStatement();
            ResultSet res = st.executeQuery("SELECT PASSWORD FROM USERS WHERE LOGIN='" + login + "'")){
            if (res.next()){
                if  (res.getString("password").equals(password.trim())) return true;
                else return false;
            }else return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isLogged(String login){
        long count = Users.getLoggedUsers().keySet().stream().filter(key -> key.equals(login)).count();
        if(count==0) return false;
        else return true;
    }
    public static void addRegisteredUser(String login, String password){
        try(Statement st = base.conDatabase().createStatement()){
            st.executeUpdate("INSERT INTO USERS(LOGIN, PASSWORD) VALUES ('" + login + "', '" + password +"')");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
