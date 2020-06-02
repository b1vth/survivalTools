package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreProcessListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(e.getPlayer().hasPermission("survivalTools.commandBypass")) return;

        if(Config.getInst().logoutBlockedCommands.contains(e.getMessage())) {
            e.getPlayer().sendMessage(ChatUtil.chat("&4Blad! &cPodczas pvp nie mozesz uzywac tej komendy!"));
            e.setCancelled(true);
        }

        if(Config.getInst().blockedCommands.contains(e.getMessage())) {
            e.getPlayer().sendMessage(Messages.getInst().getMessage("commandError"));
            e.setCancelled(true);
        }
    }
}
