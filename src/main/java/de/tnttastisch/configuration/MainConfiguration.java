package de.tnttastisch.configuration;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainConfiguration {

    private String token;
    private String prefix;

    public void create() throws IOException {
        JSONObject json = new JSONObject();
        json.put("discord.token", token);
        json.put("discord.prefix", prefix);

        File file = new File("./", "config.json");
        if (!isFileExists(file)) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(json.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    public boolean isFileExists(@NotNull File file) {
        return file.exists() ? true : false;
    }


    public void load() throws IOException {
        FileReader fileReader = new FileReader("config.json");
        if(isFileExists(new File("config.json"))) {
            JSONTokener tok = new JSONTokener(fileReader);
            JSONObject json = new JSONObject(tok);
            setToken(json.getString("discord.token"));
            setPrefix(json.getString("discord.prefix"));
            return;
        }
        System.out.println("An error occurred while executing this system. Contact the system administrator! Code: ad7ZYN2H");
    }

    public String token() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String prefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
