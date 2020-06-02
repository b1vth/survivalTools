package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.AntyLogoutManager;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    private Main inst;

    public PlayerJoinQuitListener(Main inst) {
        this.inst = inst;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(inst, () -> {
            User u = UserManager.getUser(p);
            u.updateLastJoin(p);
        });
        if(Config.getInst().removeJoinQuitMessage) e.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(Config.getInst().removeJoinQuitMessage) e.setQuitMessage(null);

        if(AntyLogoutManager.isInCombat(e.getPlayer().getUniqueId())) {
            e.getPlayer().setHealth(0.0D);
            Bukkit.broadcastMessage(Messages.getInst().getMessage("playerLogoutFromFightMessage").replace("{PLAYER_NAME}", e.getPlayer().getName()).replace("{DAMAGER_NAME}", Bukkit.getPlayer(AntyLogoutManager.getCombat(e.getPlayer().getUniqueId()).getAttacker()).getName()));
            AntyLogoutManager.removeCombat(AntyLogoutManager.getCombat(e.getPlayer().getUniqueId()).getAttacker());
            AntyLogoutManager.removeCombat(e.getPlayer().getUniqueId());
        }

        Bukkit.getScheduler().runTaskAsynchronously(inst, () -> {
           User u = UserManager.getUser(e.getPlayer());
           u.update();
        });
    }
}
