package de.tnttastisch;

import de.tnttastisch.configuration.MainConfiguration;
import de.tnttastisch.listener.MessageReceive;
import de.tnttastisch.main.DeleteHistory;
import de.tnttastisch.utils.core.Console;
import de.tnttastisch.utils.core.InOut;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DeleteAPI {


    private JDA jda;
    private Console console;
    private MainConfiguration configuration;
    private InOut inOut;

    public DeleteAPI() {
        inOut = new InOut();
        console = new Console();
        configuration = new MainConfiguration();
    }

    public void setDiscord() {
        try {
            JDABuilder builder = JDABuilder.createDefault(DeleteHistory.instance().api().configuration().token());
            System.out.println("Configure JDABuilder");
            for (GatewayIntent intent : GatewayIntent.values()) {
                builder.enableIntents(intent);
            }
            System.out.println("Enable all intents");
            builder.setActivity(Activity.competing("Delete all messages"));
            System.out.println("Set activity");
            builder.setStatus(OnlineStatus.ONLINE);
            System.out.println("Set online status to online");
            jda = builder.build();
            System.out.println("Create jda");
            jda.addEventListener(new MessageReceive());
            System.out.println("Add event listener (MessageReceive)");
            jda.awaitReady();
        } catch (InterruptedException e) {
            System.err.println(e.fillInStackTrace());
        }
    }

    public JDA discord() {
        return jda;
    }

    public Console thread() {
        return console;
    }

    public InOut inout() {
        return inOut;
    }

    public void shutdown() {
        discord().shutdown();
        System.out.println("Discord has been shut down!");
    }

    public MainConfiguration configuration() {
        return configuration;
    }

}
