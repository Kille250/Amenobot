package commands.Meme;

import Database.SQLManager;
import commands.Command;
import commands.CommandType;
import commands.EmbedManager;
import commands.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.ParseException;

public class Memeadd implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        if (args.length < 2) {
            event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Du musst schon mehr angeben du Hurensohn.")).queue();
        } else {
            try {
                URL check = new URL(args[1]);
                check.toURI();
            } catch (URISyntaxException | MalformedURLException e) {
                event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Du weißt schon was eine URL ist? Bastard.")).queue();
                return;
            }

            String uri = args[1];
            if (uri.contains(".")) {
                String extension = uri.substring(uri.lastIndexOf("."));

                 if (extension.equals(".gif") || extension.equals(".jpg") || extension.equals(".png") || extension.equals(".webm") || extension.equals(".mp4")) {

                    try {
                        SQLManager.memeRegister(event.getAuthor().getId(), args[0], args[1]);
                        event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Meme erfolgreich hinzugefügt")).queue();
                    } catch (SQLException e) {
                        event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Es gab ein Fehler beim hinzufügen.")).queue();
                    }
                } else {
                    event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Es werden derzeit nur Videos und Bilder akzeptiert.")).queue();
                }
                } else {
                event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Schonmal was von Dateiendungen oder Adressen gehört? Immer wieder das selber wichser.")).queue();
                return;
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
    public Enum<CommandType> commandType() {
        return CommandType.FREETIME;
    }

    @Override
    public Enum<Permission> permission() {
        return Permission.MEMBER;
    }
}
