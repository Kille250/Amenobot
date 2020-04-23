package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EmbedManager {
    public static MessageEmbed response(User user, String content){
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.orange)
                .setAuthor(user.getName() + "#" + user.getDiscriminator(), null, user.getAvatarUrl())
                .setDescription(content);

        return eb.build();
    }

    public static MessageEmbed profileEmbed(User user, ArrayList<String> data){
        Random rand = new Random();

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(rand.nextInt())
                .setTitle(user.getName()+"#"+user.getDiscriminator())
                .addField("ID", data.get(0), true)
                .addField("Credits", data.get(2), true)
                .addField("Beschreibung", data.get(3),false);

        if(!data.get(4).equals(" ")){
            eb.setImage(data.get(4));
        }

        return eb.build();
    }
}
