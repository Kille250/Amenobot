package commands.profile;

import Database.Content;
import Database.SQLManager;
import commands.Command;
import commands.EmbedManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

public class Setimage implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        if(args.length <1){
            event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Du musst noch eine beschissene direkte URL angeben, Bastard.")).queue();
        } else{
            String url = args[0];

            try {
                Image image = ImageIO.read(new URL(url));
                if (image != null) {
                    SQLManager.profileSetData(event.getAuthor(), args[0], Content.PBURL);
                    event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Bild wurde erfolgreich eingetragen.")).queue();
                } else {
                    event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Stirb doch einfach.")).queue();
                }
            } catch (IOException e){
                     event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "URL oder was auch immer das ist nicht valide.")).queue();
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
