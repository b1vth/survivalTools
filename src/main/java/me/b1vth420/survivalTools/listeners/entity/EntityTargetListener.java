package me.b1vth420.survivalTools.listeners.entity;

import me.b1vth420.survivalTools.tasks.HiddenMessageTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTargetListener implements Listener {

    private HiddenMessageTask task;

    public EntityTargetListener(HiddenMessageTask task) {
        this.task = task;
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if(e.getTarget() instanceof Player) {
            Player p = (Player) e.getTarget();
            if(task.isHidden(p)) e.setCancelled(true);
        }
    }
}
