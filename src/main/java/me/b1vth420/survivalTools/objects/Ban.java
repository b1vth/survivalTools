package me.b1vth420.survivalTools.objects;

import me.b1vth420.survivalTools.managers.BanManager;

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

    public UUID getUuid() { return uuid; }

    public String getName() { return name; }

    public String getAdmin() { return admin; }

    public String getReason() { return reason; }

    public long getTime() { return time; }
}
