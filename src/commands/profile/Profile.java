package commands.profile;

import Database.SQLManager;
import commands.Command;
import commands.EmbedManager;
import core.CommandParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Profile implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        ArrayList<String> data = SQLManager.profileDataGet(event.getAuthor().getId());
        if(data == null)
            event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Der Account befindet sich derzeit nicht in der Datenbank.")).queue();
        else{
            event.getTextChannel().sendMessage(EmbedManager.profileEmbed(event.getAuthor(), data)).queue();

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
