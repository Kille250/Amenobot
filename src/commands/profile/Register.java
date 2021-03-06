package commands.profile;

import Database.Content;
import Database.SQLManager;
import commands.Command;
import commands.CommandType;
import commands.EmbedManager;
import commands.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;

public class Register implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        if(SQLManager.profileDataGet(event.getAuthor().getId()) == null){
            if(SQLManager.profileSetData(event.getAuthor(), "", Content.NONE)){
                event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(),"Datensätze erfolgreich in die Datenbank eingespeichert.")).queue();
            } else{
                event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(),"Ich hasse Programmieren.")).queue();
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String description() {
        return "Hiermit registrierst du dich du Hurensohn.";
    }

    @Override
    public Enum<CommandType> commandType() {
        return CommandType.ACCOUNT;
    }

    @Override
    public Enum<Permission> permission() {
        return Permission.MEMBER;
    }
}
