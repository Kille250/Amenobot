package Database;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class LiteSQL {
    private static Connection conn;

    private static Statement stmt;

    public static boolean connect() {
        conn = null;

        try{
            File file = new File("datenbank.db");
            if(!file.exists()){
                file.createNewFile();
            }

            String url = "jdbc:sqlite:"+file.getPath();
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

            return true;


        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean disconnect() {
        if(conn != null){
            try {
                conn.close();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static void onUpdate(String sql) throws SQLException{
            stmt.execute(sql);

    }

    public static ResultSet onQuery(String sql){
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
