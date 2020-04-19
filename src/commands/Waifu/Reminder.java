package commands.Waifu;

import commands.Command;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.collections4.KeyValue;
import org.jetbrains.annotations.Async;

import java.io.IOException;
import java.security.KeyPair;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getGuild().getTextChannelsByName(args[0],true).get(0).sendMessage("@everyone Kommt Rollen ihr Wichser.").tts(true).queue();
                    }
                }, schedule().getTime(), 1000*60*60);

                if(timerList.isEmpty()){
                    ArrayList allTimers = new ArrayList();
                    allTimers.add(timer);
                    timerList.put(event.getAuthor(), allTimers);
                    event.getChannel().sendMessage("Der Timer mit der 'ID 1' wurde gesetzt.").queue();
                } else{
                    event.getChannel().sendMessage("Mehrere Timers werden derzeit nicht unterstuetzt.").queue();
                }

            } else{
                event.getTextChannel().sendMessage("Der Channel exisitiert nicht du Nutte.").queue();
            }
        } else{
            event.getTextChannel().sendMessage("lmao fk off").queue();
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
