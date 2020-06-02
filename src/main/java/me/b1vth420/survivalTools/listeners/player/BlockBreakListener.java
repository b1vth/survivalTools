package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.utils.BlockUtil;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.PolishItemNames;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Set;

public class BlockBreakListener implements Listener {

    private Main inst;

    public BlockBreakListener(Main inst) {
        this.inst = inst;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.isCancelled() && Config.getInst().detectorBlocks.contains(e.getBlock().getType()) && !e.getBlock().hasMetadata(e.getBlock().getType().toString().toLowerCase() + "X")) {
            Set<Block> blocks = BlockUtil.getConnectedblocks(e.getBlock());
            for (Block b : blocks) {
                b.setMetadata(b.getType().toString().toLowerCase() + "X", new FixedMetadataValue(inst, Boolean.valueOf(true)));
            }

            for (Player o : Bukkit.getOnlinePlayers()) {
                if (o.hasPermission("survivalTools.detector.see"))
                    o.sendMessage(ChatUtil.chat("&8 [&4ANTY&8-&4XARY&8] &8[&c" + e.getPlayer().getName() + "&8]&c Znalazl &c" + blocks.size() + " &c" + PolishItemNames.getPolishName(e.getBlock().getType()).substring(0, 1).toUpperCase() + PolishItemNames.getPolishName(e.getBlock().getType()).substring(1) + "."));
            }
        }
    }
}