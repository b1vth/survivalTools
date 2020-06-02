package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.RandomTeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;

import java.util.List;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock().getType().equals(Config.getInst().randomOnePersonTeleportButton)) {
            Block buttonBlock = e.getClickedBlock();
            Button button = (Button) buttonBlock.getState().getData();
            Block block = buttonBlock.getRelative(button.getAttachedFace());

            if (!(block.getType().equals(Config.getInst().randomTeleportBlock))) return;

            Location safeLocation = RandomTeleportUtil.getSafeLocation(e.getClickedBlock().getWorld());

            World cw = e.getPlayer().getWorld();
            cw.loadChunk(safeLocation.getBlockX(), safeLocation.getBlockZ());
            e.getPlayer().sendMessage(ChatUtil.chat("&aZaraz zostaniesz przeteleportowany!"));

            Bukkit.getScheduler().runTaskLater(Main.getInst(), new Runnable() {
                public void run() {
                    while (!safeLocation.getChunk().isLoaded()) {
                        e.getPlayer().sendMessage("&aCzekaj");
                    }

                    e.getPlayer().teleport(safeLocation);
                }
            }, 200L);
        }

        if (e.getClickedBlock().getType().equals(Config.getInst().randomMultiplePersonTeleportButton)) {
            Block buttonBlock = e.getClickedBlock();
            Button button = (Button) buttonBlock.getState().getData();
            Block block = buttonBlock.getRelative(button.getAttachedFace());

            if (!(block.getType().equals(Config.getInst().randomTeleportBlock))) return;

            Location safeLocation = RandomTeleportUtil.getSafeLocation(e.getClickedBlock().getWorld());

            safeLocation.getChunk().load(true);

            e.getPlayer().sendMessage(ChatUtil.chat("&aZaraz zostaniesz przeteleportowany!"));

            List<Player> playersOnPressurePlate = RandomTeleportUtil.getPlayersOnPressurePlate(Config.getInst().randomMultiplePersonSearchDist, buttonBlock);


            Bukkit.getScheduler().runTaskLater(Main.getInst(), new Runnable() {
                public void run() {
                    while (!safeLocation.getChunk().isLoaded()) {
                        for (Player p : playersOnPressurePlate) {
                            p.sendMessage("&aCzekaj");
                        }
                    }
                    for (Player p : playersOnPressurePlate) {
                        p.teleport(safeLocation);
                    }
                }
            }, 100L);
        }
    }
}
