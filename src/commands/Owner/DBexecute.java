package commands.Owner;

import Database.LiteSQL;
import commands.Command;
import commands.CommandType;
import commands.EmbedManager;
import commands.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DBexecute implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        String query = "";

        for(String str : args){
            if(str.equals(args[args.length-1])){
                query += str;
                break;
            }
            query += str+" ";
        }

        try {
            LiteSQL.onUpdate(query);
            event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "SQL wurde ausgeführt.")).queue();
        } catch (SQLException e){
            event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "SQL ist invalide.")).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }

    @Override
    public String help() {
        return "";
    }

    @Override
    public String description() {
        return "Dient zum Ausführen der SQL-Scripte.";
    }

    @Override
    public Enum<CommandType> commandType() {
        return CommandType.OWNER;
    }

    @Override
    public Enum<Permission> permission() {
        return Permission.BOTOWNER;
    }
}
