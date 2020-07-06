package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getEntity().sendMessage(ChatUtil.chat(Messages.getInst().getMessage("playerDeathMessage")
                .replace("[locationX]", Integer.toString(e.getEntity().getLocation().getBlockX()))
                .replace("[locationY]", Integer.toString(e.getEntity().getLocation().getBlockY()))
                .replace("[locationZ]", Integer.toString(e.getEntity().getLocation().getBlockZ()))));
        if(e.getEntity().getKiller() instanceof Player) e.setDeathMessage(null);
    }
}
