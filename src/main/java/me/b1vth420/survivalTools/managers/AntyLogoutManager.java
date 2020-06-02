package me.b1vth420.survivalTools.managers;

import me.b1vth420.survivalTools.objects.Combat;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AntyLogoutManager {

    private static ConcurrentHashMap<UUID, Combat> combats = new ConcurrentHashMap<UUID, Combat>();

    public static ConcurrentHashMap<UUID, Combat> getCombats() { return new ConcurrentHashMap<>(combats); }

    public static void addCombat(UUID p, UUID attacker) {
        Combat c = new Combat(p, attacker);
        combats.put(p, c);
        combats.put(attacker, c);
    }

    public static void removeCombat(UUID uuid) {
        if(getCombats().containsKey(uuid)) combats.remove(uuid);
    }

    public static Combat getCombat(UUID uuid) {
        if(getCombats().containsKey(uuid)) return getCombats().get(uuid);
        return null;
    }

    public static boolean isInCombat(UUID uuid) {
        if(getCombats().containsKey(uuid) && getCombats().get(uuid).getLastAttackTime() > System.currentTimeMillis()) return true;
        return false;
    }


}
