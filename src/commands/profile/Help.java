package commands.profile;

import commands.Command;
import commands.CommandType;
import commands.Permission;
import core.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Help implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        EmbedBuilder builder = new EmbedBuilder();
        HashMap<CommandType, HashMap<String, Command>> cmd = new HashMap<>();

        for(CommandType type : CommandType.values()){
            cmd.put(type, new HashMap<>());
        }

        for(Map.Entry<String, Command> cm : Main.commands.entrySet()){
            cmd.get(cm.getValue().commandType()).put(cm.getKey(), cm.getValue());
        }

        for(Map.Entry<CommandType, HashMap<String, Command>> iter: cmd.entrySet()) {
            StringBuilder content = new StringBuilder();

            for(Map.Entry<String, Command> add : iter.getValue().entrySet()){
                content.append(add.getKey()+": "+add.getValue().description()+"\n");
            }

            builder.addField(iter.getKey().name(),content.toString(), false);
        }

        event.getTextChannel().sendMessage(builder.build()).queue();
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
        return "Zeigt dir alle Commands an.";
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
