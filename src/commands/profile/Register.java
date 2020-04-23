package commands.profile;

import Database.Content;
import Database.SQLManager;
import commands.Command;
import commands.EmbedManager;
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
        return null;
    }

    @Override
    public String commandType() {
        return null;
    }

    @Override
    public int permission() {
        return 0;
    }
}
