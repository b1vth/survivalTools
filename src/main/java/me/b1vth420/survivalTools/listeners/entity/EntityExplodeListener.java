package me.b1vth420.survivalTools.listeners.entity;

import me.b1vth420.survivalTools.data.configs.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if(Config.getInst().explodeBlock.contains(e.getEntityType())) e.setCancelled(true);
    }
}
