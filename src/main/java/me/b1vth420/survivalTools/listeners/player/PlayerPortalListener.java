package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.data.configs.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PlayerPortalListener implements Listener {

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if(Config.getInst().repairNetherPortals) e.setSearchRadius(1);
    }
}
