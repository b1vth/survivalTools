package me.b1vth420.survivalTools.managers;

import me.b1vth420.survivalTools.objects.BanIP;

import java.util.concurrent.ConcurrentHashMap;

public class BanIPManager {
    private static final ConcurrentHashMap<String, BanIP> bans = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, BanIP> getBans() { return new ConcurrentHashMap<>(bans); }

    public static void addBan(BanIP b) { if(!getBans().contains(b.getIP())) bans.put(b.getIP(), b); }

    public static void removeBan(BanIP b) {
        if(getBans().containsKey(b.getIP())) bans.remove(b.getIP());
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
