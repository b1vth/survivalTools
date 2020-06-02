package me.b1vth420.survivalTools.utils;

import me.b1vth420.survivalTools.interfaces.TimerInterface;
import me.b1vth420.survivalTools.managers.TimerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportUtil {
    public static void teleport(Player p, Location l, int time, String location) {
        //TODO Checking antylogout
        p.sendMessage(ChatUtil.chat("&3[Uwaga] &bTeleportacja nastapi za &6" + time + "&b sekund"));
        TimerManager.addTask(p, new TimerInterface<Player>() {
            @Override
            public void success(Player p) {
                p.teleport(l);
                p.sendMessage(ChatUtil.chat("&bZostales przeteleportowany" + (location.isEmpty() ? "!": (location + "!"))));
            }

            @Override
            public void error(Player p) {
                p.sendMessage(ChatUtil.chat("&4Blad! &cPoruszyles sie teleportacja przerwana!"));
            }
        }, time);
    }
}
