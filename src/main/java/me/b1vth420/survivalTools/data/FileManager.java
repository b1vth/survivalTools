package me.b1vth420.survivalTools.data;

import me.b1vth420.survivalTools.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileManager {

    private static YamlConfiguration lang;

    public FileManager() {
        check();
    }

    private static File mainDir = Main.getInst().getDataFolder();
    private static File cfgFile = new File(mainDir, "config.yml");
    private static File langFile = new File(mainDir, "messages.yml");

    public static void check(){
        if(!(mainDir.exists())) mainDir.mkdir();
        if(!(cfgFile.exists())) Main.getInst().saveDefaultConfig();
        if(!(langFile.exists())) Main.getInst().saveResource("messages.yml", true);

        lang = YamlConfiguration.loadConfiguration(langFile);
    }

    public static File getMainDir() {
        return mainDir;
    }

    public static YamlConfiguration getLang(){
        return lang;
    }
}
