package me.b1vth420.survivalTools.tasks;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class HiddenMessageTask implements Runnable {

    private Main inst;

    public HiddenMessageTask(Main inst) {
        this.inst = inst;
    }

    private static HashSet<UUID> hiddenPlayers = new HashSet<>();

    @Override
    public void run() {
        for(UUID uuid : hiddenPlayers) {
            Player p = Bukkit.getPlayer(uuid);
            if(p == null) {
                hiddenPlayers.remove(uuid);
                continue;
            }
            p.sendActionBar(ChatUtil.chat("&aJestes niewidoczny"));
        }
    }

    public void addHiddenPlayer(Player p) {
        hiddenPlayers.add(p.getUniqueId());
        p.hidePlayer(inst, p);
        p.sendMessage(Messages.getInst().getMessage("vanishChangeMessage").replace("{MODE}", ChatUtil.chat("&aniewidzialny")));
        p.sendActionBar(ChatUtil.chat("&aJestes niewidzialny"));
        p.setPlayerListName(" ");
    }

    public void removeHiddenPlayer(Player p) {
        hiddenPlayers.remove(p.getUniqueId());
        p.showPlayer(inst, p);
        p.sendMessage(Messages.getInst().getMessage("vanishChangeMessage").replace("{MODE}", ChatUtil.chat("&cwidzialny")));
        p.sendActionBar(ChatUtil.chat("&cJestes widzialny"));
        p.setPlayerListName(p.getDisplayName());
    }

    public boolean isHidden(Player p) {
        return hiddenPlayers.contains(p.getUniqueId());
    }
}
