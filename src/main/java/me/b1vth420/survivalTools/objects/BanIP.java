package me.b1vth420.survivalTools.objects;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.managers.BanIPManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;
import java.util.UUID;

public class BanIP {

    private UUID uuid;
    private String ip;
    private String name;
    private String admin;
    private String reason;
    private long time;

    //TODO mysql/mongodb (now only flat :/)

    public BanIP(UUID uuid, String ip, String name, String admin, String reason, long time) {
        this.uuid = uuid;
        this.ip = ip;
        this.name = name;
        this.admin = admin;
        this.reason = reason;
        this.time = time;
        BanIPManager.addBan(this);
    }

    public BanIP(YamlConfiguration cfg) {
        this.uuid = UUID.fromString(cfg.getString("uuid"));
        this.name = cfg.getString("name");
        this.ip = cfg.getString("ip");
        this.admin = cfg.getString("admin");
        this.reason = cfg.getString("reason");
        this.time = cfg.getLong("time");
        BanIPManager.addBan(this);
    }

    public BanIP(Map<String, Object> resultSet) {
        this.uuid = UUID.fromString((String) resultSet.get("UUID"));
        this.ip = (String) resultSet.get("IP");
        this.name = (String) resultSet.get("name");
        this.admin = (String) resultSet.get("admin");
        this.reason = (String) resultSet.get("reason");
        this.time = (Long) resultSet.get("time");
        BanIPManager.addBan(this);
    }

    public UUID getUuid() { return uuid; }

    public String  getIP() { return ip; }

    public String getName() { return name; }

    public String getAdmin() { return admin; }

    public String getReason() { return reason; }

    public long getTime() { return time; }

    public void update() {
        Main.getInst().getSQLManager().executeUpdate("INSERT INTO tools_BansIP(UUID, IP, name, admin, reason, time) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE IP=?, name=?, admin=?, reason=?, time=?"
                , new String[] {
                        this.uuid.toString(),
                        this.ip,
                        this.name,
                        this.admin,
                        this.reason,
                        String.valueOf(this.time),
                        this.ip,
                        this.name,
                        this.admin,
                        this.reason,
                        String.valueOf(this.time),
                });
    }
}
