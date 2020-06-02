package me.b1vth420.survivalTools.managers;

import me.b1vth420.survivalTools.objects.User;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private static final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<UUID, User> getUsers() { return new ConcurrentHashMap<>(users); }

    public static void addUser(User u) { if(!getUsers().contains(u.getUuid())) users.put(u.getUuid(), u); }

    public static void removeUser(User u) { if(getUsers().contains(u.getUuid())) users.remove(u.getUuid()); }

    public static User getUser(Player p) {
        User u = getUsers().get(p.getUniqueId());
        if(u != null) return u;
        return new User(p);
    }

    public static User getUser(UUID uuid) {
        User u = getUsers().get(uuid);
        if(u != null) return u;
        return null;
    }

    public static User getUser(String name) {
        for(User u : getUsers().values()) {
            if(u.getName().equals(name)) return u;
        }
        return null;
    }
}
