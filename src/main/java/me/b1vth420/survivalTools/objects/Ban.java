package me.b1vth420.survivalTools.objects;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.managers.BanManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Map;
import java.util.UUID;

public class Ban {

    private UUID uuid;
    private String name;
    private String admin;
    private String reason;
    private long time;

    //TODO mysql/mongodb (now only flat :/)

    public Ban(UUID uuid, String name, String admin, String reason, long time) {
        this.uuid = uuid;
        this.name = name;
        this.admin = admin;
        this.reason = reason;
        this.time = time;
        BanManager.addBan(this);
    }

    public Ban(YamlConfiguration cfg) {
        this.uuid = UUID.fromString(cfg.getString("uuid"));
        this.name = cfg.getString("name");
        this.admin = cfg.getString("admin");
        this.reason = cfg.getString("reason");
        this.time = cfg.getLong("time");
        BanManager.addBan(this);
    }

    public Ban(Map<String, Object> resultSet) {
        this.uuid = UUID.fromString((String) resultSet.get("UUID"));
        this.name = (String) resultSet.get("name");
        this.admin = (String) resultSet.get("admin");
        this.reason = (String) resultSet.get("reason");
        this.time = (Long) resultSet.get("time");
        BanManager.addBan(this);
    }

    public UUID getUuid() { return uuid; }

    public String getName() { return name; }

    public String getAdmin() { return admin; }

    public String getReason() { return reason; }

    public long getTime() { return time; }

    public void update() {
        Main.getInst().getSQLManager().executeUpdate("INSERT INTO tools_Bans(UUID, name, admin, reason, time) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE name=?, admin=?, reason=?, time=?"
                , new String[] {
                        this.getUuid().toString(),
                        this.name,
                        this.admin,
                        this.reason,
                        String.valueOf(this.time),
                        this.name,
                        this.admin,
                        this.reason,
                        String.valueOf(this.time),
                });
    }
}
