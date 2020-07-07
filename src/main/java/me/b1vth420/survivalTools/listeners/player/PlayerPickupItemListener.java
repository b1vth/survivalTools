package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.tasks.HiddenMessageTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerPickupItemListener implements Listener {

    private HiddenMessageTask task;

    public PlayerPickupItemListener(HiddenMessageTask task) {
        this.task = task;
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if(task.isHidden(p)) {
            e.setCancelled(true);
        }
    }
}
