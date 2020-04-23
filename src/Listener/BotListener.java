package Listener;

import Database.SQLManager;
import commands.Command;
import commands.CommandType;
import commands.EmbedManager;
import commands.Permission;
import core.CommandParser;
import core.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.text.ParseException;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!event.getMessage().getAuthor().isBot()){
            try {
                CommandParser.CommandContainer cc = Main.parser.parse(event.getMessage().getContentRaw(), event);
                Command c = Main.commands.get(cc.invoke.toLowerCase());
                if(c == null){
                    return;
                }

                if(c.permission() == Permission.BOTOWNER && event.getAuthor().getId().equals("178558083688169472")
                        || c.permission() == Permission.ADMIN && event.getMember().hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR)
                        || c.permission() == Permission.MEMBER
                ){
                    if(SQLManager.profileDataGet(event.getAuthor().getId()) != null) {
                        Main.handleCommand(cc);
                    } else {
                        if(c.commandType() == CommandType.ACCOUNT){
                            Main.handleCommand(cc);
                        }else
                            event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Du musst ein Profil dafür haben.")).queue();
                    }
                } else {
                    event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Du Hurensohn hast nicht die erforderlichen Berechtigungen.")).queue();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
