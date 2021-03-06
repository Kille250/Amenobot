package commands.Waifu;

import commands.Command;
import commands.CommandType;
import commands.EmbedManager;
import commands.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Reminder implements Command {
    public Map<User, ArrayList<Timer>> timerList = new HashMap<>();

    private Calendar schedule(){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, today.get(Calendar.HOUR_OF_DAY));
        today.set(Calendar.MINUTE, 49);

        return today;
    };

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        if(args.length==1){
            if(event.getGuild().getTextChannelsByName(args[0], true).toArray().length > 0){
                if(timerList.isEmpty()){
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            event.getGuild().getTextChannelsByName(args[0],true).get(0).sendMessage(EmbedManager.response(event.getAuthor(),"@everyone Kommt Rollen ihr Wichser.")).queue();
                        }
                    }, schedule().getTime(), 1000*60*60);

                    ArrayList allTimers = new ArrayList();
                    allTimers.add(timer);
                    timerList.put(event.getAuthor(), allTimers);
                    event.getChannel().sendMessage(EmbedManager.response(event.getAuthor(),"Der Timer wurde erstellt.")).queue();
                } else{
                    event.getChannel().sendMessage(EmbedManager.response(event.getAuthor(),"Mehrere Timers werden derzeit nicht unterstuetzt.")).queue();
                }

            } else{
                event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(), "Der Channel exisitiert nicht du Nutte.")).queue();
            }
        } else{
            event.getTextChannel().sendMessage(EmbedManager.response(event.getAuthor(),"lmao fk off")).queue();
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
        return "Hiermit wird der stündliche Reminder zum Bitches ziehen aktiviert.";
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
