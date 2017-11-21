package server;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SqlHandler {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psFindNickByLoginPass;

    public static void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        DriverManager.getConnection("jdbc:sqlite:users.db");
        stmt = connection.createStatement();
//         prepare();
         psFindNickByLoginPass = connection.prepareStatement("Select nick From client Where login = ? AND password ? ;");
    }
    public static String getNickByLoginPass(String login, String pass) {
        try {


            psFindNickByLoginPass.setString(1, login);
            psFindNickByLoginPass.setString(2, pass);
            ResultSet rs = psFindNickByLoginPass.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void prepare() {
        try {
            stmt.executeUpdate("CREATE TABLE IF NOT EXIST clients (id INTEGER, login TEXT, passwords TEXT, nick TEXT);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

