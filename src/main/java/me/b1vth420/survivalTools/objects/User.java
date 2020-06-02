package me.b1vth420.survivalTools.objects;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.managers.UserManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
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

    public User(YamlConfiguration cfg) {
        this.name = cfg.getString("name");
        this.uuid = UUID.fromString(cfg.getString("uuid"));
        this.ip = cfg.getString("ip");
        this.firstJoin = cfg.getLong("firstJoin");
        this.lastJoin = cfg.getLong("lastJoin");
        this.msg = cfg.getBoolean("msg");
        UserManager.addUser(this);
    }

    public User(Map<String, Object> resultSet) {
        this.name = (String) resultSet.get("name");
        this.uuid = UUID.fromString((String) resultSet.get("UUID"));
        this.ip = (String) resultSet.get("ip");
        this.firstJoin = (Long) resultSet.get("firstJoin");
        this.lastJoin = (Long) resultSet.get("lastJoin");
        this.msg = Boolean.valueOf((String) resultSet.get("msg"));
        UserManager.addUser(this);
    }

    public String getName() { return name; }

    public UUID getUuid() { return uuid; }

    public String getIp() { return ip; }

    public long getFirstJoin() { return firstJoin; }

    public long getLastJoin() { return lastJoin; }

    public boolean isMsg() { return msg; }

    public void changeMsg() { this.msg = !this.msg; }

    public void updateLastJoin(Player p) {
        this.lastJoin = System.currentTimeMillis();
        this.ip = p.getAddress().getAddress().getHostAddress().replace("/", "");
    }

    public void update() {

        Main.getInst().getSQLManager().executeUpdate("INSERT INTO tools_Users(UUID, name, ip, firstJoin, lastJoin, msgEnabled) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE name=?, ip=?, lastJoin=?, msgEnabled=?"
                , new String[] {
                        this.getUuid().toString(),
                        this.name,
                        this.ip,
                        String.valueOf(this.firstJoin),
                        String.valueOf(this.lastJoin),
                        String.valueOf(this.msg),
                        this.name,
                        this.ip,
                        String.valueOf(this.lastJoin),
                        String.valueOf(this.msg),
                });
    }
}
