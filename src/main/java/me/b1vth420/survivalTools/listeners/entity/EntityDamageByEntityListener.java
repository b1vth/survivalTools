package me.b1vth420.survivalTools.listeners.entity;

import me.b1vth420.survivalTools.managers.AntyLogoutManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            AntyLogoutManager.addCombat(((Player)e.getEntity()).getUniqueId(), ((Player)e.getDamager()).getUniqueId());
        }
    }
}
