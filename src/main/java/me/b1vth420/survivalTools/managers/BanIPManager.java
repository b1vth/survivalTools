package me.b1vth420.survivalTools.managers;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.DataSaveType;
import me.b1vth420.survivalTools.data.FileManager;
import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.objects.BanIP;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class BanIPManager {
    private static final ConcurrentHashMap<String, BanIP> bans = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, BanIP> getBans() { return new ConcurrentHashMap<>(bans); }

    public static void addBan(BanIP b) { if(!getBans().contains(b.getIP())) bans.put(b.getIP(), b); }

    public static void removeBan(BanIP b) {
        if (getBans().containsKey(b.getIP())) bans.remove(b.getIP());
        if (Config.getInst().dataSaveType == DataSaveType.FLAT) {
            File toRemove = new File(FileManager.getIpBansDir(), b.getName() + ".yml");
            if (toRemove.exists()) toRemove.delete();
        }
        if(Config.getInst().dataSaveType == DataSaveType.MYSQL) {
            Main.getInst().getSQLManager().execute("DELETE FROM tools_BansIP WHERE uuid = '" + b.getUuid().toString() + "'");
        }
    }

    public static BanIP getBan(String ip) {
        BanIP b = getBans().get(ip);
        if(b != null) return b;
        return null;
    }

    public static BanIP getBanName(String name) {
        for(BanIP b : getBans().values()) {
            if(b.getName().equals(name)) return b;
        }
        return null;
    }
}
