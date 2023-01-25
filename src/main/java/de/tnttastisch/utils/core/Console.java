package de.tnttastisch.utils.core;

import de.tnttastisch.main.DeleteHistory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import static de.tnttastisch.utils.core.InOut.Input;

public class Console extends Thread {
    private boolean running = true;

    @Override
    public void run() {
        try {
            systemLoad();
            Scanner scanner = new Scanner(System.in);
            while (running) {
                String input = scanner.nextLine();
                switch(input.toLowerCase()){
                    case "stop", "end" -> {
                        System.out.println("Exiting...");
                        shutdown();
                    }
                    case "help" -> {
                        System.out.println("Help-List:\nend - Shutdown the bot");
                    }
                    default -> {
                        System.out.println("There is no command, type help for help!");
                    }
                }
                if(running) {
                    Thread.sleep(1000);
                } else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("An error occurred while executing this system. Contact the system administrator! Code: H5h0IRFI");
            System.err.println(e.fillInStackTrace());
        }
    }

    public void shutdown() throws InterruptedException {
        System.out.println("Waiting for discord to shutdown...");
        DeleteHistory.instance().api().shutdown();
        Thread.sleep(1000);
        System.out.println("Bye!");
        Thread.sleep(200);
        running = false;
        interrupt();
    }

    private void systemLoad() {
        if (!DeleteHistory.instance().api().configuration().isFileExists(new File("config.json"))) {
            DeleteHistory.instance().api().configuration().setToken(DeleteHistory.instance().api().inout().readString("Please enter a discord token.\n> "));
            DeleteHistory.instance().api().configuration().setPrefix(DeleteHistory.instance().api().inout().readString("Please enter a message prefix.\n> "));
            try {
                DeleteHistory.instance().api().configuration().create();
            } catch (IOException e) {
                System.err.println(e.fillInStackTrace());
            }
        }
        try {
            DeleteHistory.instance().api().configuration().load();
        } catch (IOException e) {
            System.out.println("An error occurred while executing this system. Contact the system administrator! Code: 6ZuUWTY8");
            System.err.println(e.fillInStackTrace());
        }
        System.out.println("Loading dependencies...");
        DeleteHistory.instance().api().setDiscord();
    }
}
