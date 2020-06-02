package me.b1vth420.survivalTools.data.flat;

import me.b1vth420.survivalTools.data.FileManager;
import me.b1vth420.survivalTools.managers.BanIPManager;
import me.b1vth420.survivalTools.managers.BanManager;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.Ban;
import me.b1vth420.survivalTools.objects.BanIP;
import me.b1vth420.survivalTools.objects.User;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataManager {

    public static void sendConsole(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chat(msg));
    }

    public static void loadAll() {
        loadUsers();
        loadBans();
        loadIPBans();
    }

    public static void saveAll() {
        try {
            saveUsers();
            saveBans();
            saveIPBans();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUsers() throws IOException {
        int saved = 0;
        for(User u : UserManager.getUsers().values()) {
            File f = new File(FileManager.getDataDir(), u.getName() + ".yml");
            if(!f.exists()) f.createNewFile();
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            cfg.set("name", u.getName());
            cfg.set("uuid", u.getUuid().toString());
            cfg.set("ip", u.getIp());
            cfg.set("firstJoin", u.getFirstJoin());
            cfg.set("lastJoin", u.getFirstJoin());
            cfg.set("msg", u.isMsg());
            cfg.save(f);
            saved++;
        }
        sendConsole("&6&l" + saved + " &a&lUsers saved!");
    }

    private static void saveBans() throws IOException {
        int saved = 0;
        for(Ban b : BanManager.getBans().values()) {
            File f = new File(FileManager.getDataDir(), b.getName() + ".yml");
            if(!f.exists()) f.createNewFile();
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            cfg.set("name", b.getName());
            cfg.set("uuid", b.getUuid().toString());
            cfg.set("admin", b.getAdmin());
            cfg.set("reason", b.getReason());
            cfg.set("time", b.getTime());
            cfg.save(f);
            saved++;
        }
        sendConsole("&6&l" + saved + " &a&lBans saved!");
    }
    private static void saveIPBans() throws IOException {
        int saved = 0;
        for(BanIP b : BanIPManager.getBans().values()) {
            File f = new File(FileManager.getDataDir(), b.getName() + ".yml");
            if(!f.exists()) f.createNewFile();
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            cfg.set("name", b.getName());
            cfg.set("uuid", b.getUuid().toString());
            cfg.set("ip", b.getIP());
            cfg.set("admin", b.getAdmin());
            cfg.set("reason", b.getReason());
            cfg.set("time", b.getTime());
            cfg.save(f);
            saved++;
        }
        sendConsole("&6&l" + saved + " &a&lIPBans saved!");
    }


    private static void loadUsers() {
        int loaded = 0;
        for(File f : FileManager.getDataDir().listFiles()) {
            new User(YamlConfiguration.loadConfiguration(f));
            loaded++;
        }
        sendConsole("&6&l" + loaded + " &a&lUsers loaded!");
    }

    private static void loadBans() {
        int loaded = 0;
        for(File f : FileManager.getBansDir().listFiles()) {
            new Ban(YamlConfiguration.loadConfiguration(f));
            loaded++;
        }
        sendConsole("&6&l" + loaded + " &a&lBans loaded!");
    }

    private static void loadIPBans() {
        int loaded = 0;
        for(File f : FileManager.getBansDir().listFiles()) {
            new BanIP(YamlConfiguration.loadConfiguration(f));
            loaded++;
        }
        sendConsole("&6&l" + loaded + " &a&lIPBans loaded!");
    }

    public static void saveUser(User u) {
        File f = new File(FileManager.getDataDir(), u.getName() + ".yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.set("name", u.getName());
        cfg.set("uuid", u.getUuid().toString());
        cfg.set("ip", u.getIp());
        cfg.set("firstJoin", u.getFirstJoin());
        cfg.set("lastJoin", u.getFirstJoin());
        cfg.set("msg", u.isMsg());

        try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

