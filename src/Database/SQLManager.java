package Database;

import net.dv8tion.jda.api.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLManager {

    public static void onCreate(){

        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS USER(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid VARCHAR NOT NULL, currency INTEGER, description VARCHAR, pburl VARCHAR)");
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
}
