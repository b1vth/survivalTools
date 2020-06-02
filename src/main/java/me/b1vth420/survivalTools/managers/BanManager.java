package me.b1vth420.survivalTools.managers;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.DataSaveType;
import me.b1vth420.survivalTools.data.FileManager;
import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.objects.Ban;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BanManager {


    private static final ConcurrentHashMap<UUID, Ban> bans = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<UUID, Ban> getBans() { return new ConcurrentHashMap<>(bans); }

    public static void addBan(Ban b) { if(!getBans().contains(b.getUuid())) bans.put(b.getUuid(), b); }

    public static void removeBan(Ban b) {
        if(getBans().containsKey(b.getUuid())) bans.remove(b.getUuid());
        if(Config.getInst().dataSaveType == DataSaveType.FLAT) {
            File toRemove = new File(FileManager.getBansDir(), b.getName() + ".yml");
            if(toRemove.exists()) toRemove.delete();
        }
        if(Config.getInst().dataSaveType == DataSaveType.MYSQL) {
            Main.getInst().getSQLManager().execute("DELETE FROM tools_Bans WHERE uuid = '" + b.getUuid().toString() + "'");
        }
    }

    public static Ban getBan(Player p) {
        Ban b = getBans().get(p.getUniqueId());
        if(b != null) return b;
        return null;
    }

    public static Ban getBan(UUID uuid) {
        Ban b = getBans().get(uuid);
        if(b != null) return b;
        return null;
    }

    public static Ban getBan(String name) {
        for(Ban b : getBans().values()) {
            if(b.getName().equals(name)) return b;
        }
        return null;
    }
}
