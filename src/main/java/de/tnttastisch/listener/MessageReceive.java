package de.tnttastisch.listener;

import de.tnttastisch.main.DeleteHistory;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

public class MessageReceive extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        TextChannel textChannel = message.getGuildChannel().asTextChannel();
        String prefix = DeleteHistory.instance().api().configuration().prefix();

        if (!(content.startsWith(prefix))) {
            textChannel.sendMessage("There is no command with that argument! Use -clear");
            return;
        }

        for(Guild guild : DeleteHistory.instance().api().discord().getGuilds()) {
            for(Member member : guild.getMembers()) {
                if(message.getAuthor() == member.getUser()) {
                    if(!(member.hasPermission(Permission.ADMINISTRATOR))) {
                        textChannel.sendMessage("No permission!").queue();
                        System.out.println(message.getAuthor().getAsTag() + " has no permission!");
                        return;
                    }
                }
            }
        }

        if (content.equalsIgnoreCase(prefix + "clear")) {
            MessageHistory history = MessageHistory.getHistoryFromBeginning(textChannel).complete();
            List<Message> messageHistory = history.getRetrievedHistory();
            for(Message messageFromHistory : messageHistory){
                messageFromHistory.delete().reason("Determination!");
            }
            textChannel.sendMessage("Messages successfully deleted").queue();
            System.out.println(message.getAuthor().getAsTag() + " deleted all messages in " + textChannel.getName() + ". (" + content + ")");
            return;
        }

        System.out.println(message.getAuthor().getAsTag() + " executed command with set prefix in " + textChannel.getName() + ". (" + content + ")");
    }
}
