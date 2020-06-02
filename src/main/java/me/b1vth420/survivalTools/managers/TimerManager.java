package me.b1vth420.survivalTools.managers;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.interfaces.TimerInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerManager implements Listener {

    private static Map<UUID, TimerTask> tasks;

    static {
        tasks = new HashMap<>();
    }

    public static void addTask(Player p, TimerInterface<Player> call, int time) {
        TimerTask t = TimerManager.tasks.get(p.getUniqueId());
        if(t != null) {
            t.cancel(p);
            return;
        }
        t = new TimerTask(p.getUniqueId(), call);
        TimerManager.tasks.put(p.getUniqueId(), t);
        t.runTaskLater(Main.getInst(), time*20);
    }

    private static void cancel(final TimerTask task, final Player player) {
        task.cancel(player);
        TimerManager.tasks.remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMove(final PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
            return;
        }
        final TimerTask t = TimerManager.tasks.get(event.getPlayer().getUniqueId());
        if (t != null) {
            cancel(t, event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player p = (Player)event.getEntity();
            final TimerTask t = TimerManager.tasks.get(p.getUniqueId());
            if (t != null) {
                cancel(t, p);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onQuit(final PlayerQuitEvent event) {
        final Player p = event.getPlayer();
        final TimerTask t = TimerManager.tasks.get(p.getUniqueId());
        if (t != null) {
            cancel(t, p);
        }
    }

    public static class TimerTask extends BukkitRunnable {
        private UUID player;
        private TimerInterface<Player> call;

        public void run() {
            this.call.success(Bukkit.getPlayer(this.player));
            TimerManager.tasks.remove(this.player);
        }

        public void cancel(final Player player) {
            super.cancel();
            this.call.error(player);
        }

        public TimerTask(final UUID player, final TimerInterface<Player> call) {
            super();
            this.player = player;
            this.call = call;
        }

        public UUID getPlayer() {
            return this.player;
        }
    }
}
