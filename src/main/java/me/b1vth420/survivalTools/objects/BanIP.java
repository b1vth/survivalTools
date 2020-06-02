package me.b1vth420.survivalTools.objects;

import me.b1vth420.survivalTools.managers.BanIPManager;

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

    public UUID getUuid() { return uuid; }

    public String  getIP() { return ip; }

    public String getName() { return name; }

    public String getAdmin() { return admin; }

    public String getReason() { return reason; }

    public long getTime() { return time; }
}
