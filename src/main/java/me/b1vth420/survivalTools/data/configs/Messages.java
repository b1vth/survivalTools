package me.b1vth420.survivalTools.data.configs;

import me.b1vth420.survivalTools.data.FileManager;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class Messages {

    private FileConfiguration cfg = FileManager.getLang();

    private static Messages inst;

    public Messages() {
        inst = this;
        load();
    }

    public HashMap<String, String> messages;

    public void load() {
        messages = new HashMap<>();
        ConfigurationSection cs = cfg.getConfigurationSection("messages");
        for(String s : cs.getKeys(false)) {
            messages.put(s, cs.getString(s));
        }
    }

    public  String getMessage(String s) {
        return ChatUtil.chat(messages.get(s));
    }

    public static Messages getInst() {
        if (inst == null) return new Messages();
        return inst;
    }

}
