package core;

import Database.LiteSQL;
import Listener.BotListener;
import commands.Waifu.Reminder;
import commands.test.Test;
import core.CommandParser;
import net.dv8tion.jda.api.*;
import commands.Command;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import commands.*;

public class Main {
    static JDABuilder builder;

    public static final CommandParser parser = new CommandParser();

    public static HashMap<String, Command> commands = new HashMap<>();

    public static JDA jda;

    public static void main(String[] args){
        LiteSQL.connect();

        builder = new JDABuilder(AccountType.BOT)
                .setToken("NzAxMTcwODIzNTMyMzgwMjAy.XptnUw.CnrBXQWMXlJHlLTwQheqBV4r7ks")
                .setAutoReconnect(true)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.streaming("nigpro records", "https://twitch.tv/nigpro"));

        initiliazeListeners();
        initiliazeCommands();

        try {
            builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private static void initiliazeListeners(){
        builder.addEventListeners(new BotListener());
    }

    private static void initiliazeCommands(){
        commands.put("test", new Test());
        commands.put("reminder", new Reminder());
    }

    public static void handleCommand(CommandParser.CommandContainer cmd) throws ParseException, IOException {

        if (commands.containsKey(cmd.invoke.toLowerCase())) {
            boolean safe = commands.get(cmd.invoke.toLowerCase()).called(cmd.args, cmd.event);

            if (!safe) {
                commands.get(cmd.invoke.toLowerCase()).action(cmd.args, cmd.event);
                commands.get(cmd.invoke.toLowerCase()).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke.toLowerCase()).executed(safe, cmd.event);
            }

            List<TextChannel> tcs = cmd.event.getGuild().getTextChannelsByName("logging", true);
            if (tcs.size() > 0) {
                User author = cmd.event.getAuthor();
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(Color.orange)
                        .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getAvatarUrl())
                        .setDescription("```" + cmd.event.getMessage().getContentRaw() + "```");
                tcs.get(0).sendMessage(eb.build()).queue();
            }
        }
    }

}
