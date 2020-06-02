package me.b1vth420.survivalTools.objects;

import me.b1vth420.survivalTools.managers.UserManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private String name;
    private UUID uuid;
    private String ip;
    private long firstJoin;
    private long lastJoin;
    private boolean msg;

    //TODO mysql/mongodb (now only flat :/)

    public User(Player p) {
        this.name = p.getName();
        this.uuid = p.getUniqueId();
        this.ip = p.getAddress().getAddress().getHostAddress().replace("/", "");
        this.firstJoin = System.currentTimeMillis();
        this.lastJoin = System.currentTimeMillis();
        this.msg = true;
        UserManager.addUser(this);
    }

    public String getName() { return name; }

    public UUID getUuid() { return uuid; }

    public String getIp() { return ip; }

    public long getFirstJoin() { return firstJoin; }

    public long getLastJoin() { return lastJoin; }

    public boolean isMsg() { return msg; }

    public void updateLastJoin(Player p) {
        this.lastJoin = System.currentTimeMillis();
        this.ip = p.getAddress().getAddress().getHostAddress().replace("/", "");
    }
}
