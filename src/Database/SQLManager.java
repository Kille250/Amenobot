package Database;

import net.dv8tion.jda.api.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class SQLManager {

    public static void onCreate(){

        try {
            LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS USER(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid VARCHAR NOT NULL, currency INTEGER, description VARCHAR, pburl VARCHAR)");
            LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS MEMES(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,userid VARCHAR, character VARCHAR, memeurl VARCHAR, timestamp VARCHAR)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> profileDataGet(String id){
        ArrayList<String> data = new ArrayList<>();

        try {
            ResultSet rs = LiteSQL.onQuery("SELECT * FROM USER WHERE userid = " + id);

            while (rs.next()){
                data.add(rs.getString("id"));
                data.add(rs.getString("userid"));
                data.add(rs.getString("currency"));
                data.add(rs.getString("description"));
                data.add(rs.getString("pburl"));
                return data;
            }
        } catch(SQLException e){
            return null;
        }
        return null;
    };

    public static boolean profileSetData(User user, String data, Enum content){
        ResultSet set = LiteSQL.onQuery("SELECT * FROM USER WHERE id = " + user.getId());
        try {
            if(content.equals(Content.NONE)) {
                if (set.next() == false) {
                    LiteSQL.onUpdate("INSERT INTO USER(userid, currency, description, pburl) VALUES(" + user.getId() + ", 1000, 'Empty', ' ')");
                    return true;
                }
            } else if (content.equals(Content.PBURL)) {
                    LiteSQL.onUpdate("UPDATE USER SET pburl = '" + data + "' WHERE userid ='" + user.getId()+"'");
                    return true;
                }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean memeRegister(String userid, String tag, String url) throws SQLException {
            LiteSQL.onUpdate("INSERT INTO MEMES(userid, character, memeurl) VALUES('"+userid+"', '"+tag+"', '"+url+"')");
            return true;
    }

    public static ArrayList<String> memeGet(String id, boolean random) throws SQLException {
        ArrayList<String> array = new ArrayList<>();
        if(random == true){
            ResultSet length = LiteSQL.onQuery("SELECT COUNT(*) FROM MEMES");

            if(length != null){
                Random rand = new Random();
                Integer amount = rand.nextInt((Integer.parseInt(length.getString(1))-1)+1)+1;
                ResultSet data = LiteSQL.onQuery("SELECT * FROM MEMES WHERE id = "+amount);
                while(data.next()){
                    array.add(data.getString("id"));
                    array.add(data.getString("userid"));
                    array.add(data.getString("character"));
                    array.add(data.getString("memeurl"));
                    array.add(data.getString("timestamp"));
                }
                return array;

            } else{
                return null;
            }
        } else{
            return null;
        }
    }
}
