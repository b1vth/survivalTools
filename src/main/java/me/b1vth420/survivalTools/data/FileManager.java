package me.b1vth420.survivalTools.data;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Config;
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
    private static File dataDir = new File(mainDir, "data");
    private static File bansDir = new File(mainDir, "bans");
    private static File ipBansDir = new File(mainDir, "ipBans");

    public static void check(){
        if(!(mainDir.exists())) mainDir.mkdir();
        if(!(cfgFile.exists())) Main.getInst().saveDefaultConfig();
        if(!(langFile.exists())) Main.getInst().saveResource("messages.yml", true);
        lang = YamlConfiguration.loadConfiguration(langFile);
    }

    public void checkFlat() {
        if(!(dataDir.exists()) && Config.getInst().dataSaveType == DataSaveType.FLAT) dataDir.mkdir();
        if(!(bansDir.exists()) && Config.getInst().dataSaveType == DataSaveType.FLAT) bansDir.mkdir();
        if(!(ipBansDir.exists()) && Config.getInst().dataSaveType == DataSaveType.FLAT) ipBansDir.mkdir();
    }

    public static File getMainDir() { return mainDir; }

    public static File getDataDir() { return  dataDir; }

    public static File getBansDir() { return bansDir; }

    public static File getIpBansDir() { return ipBansDir; }

    public static YamlConfiguration getLang(){
        return lang;
    }
}
