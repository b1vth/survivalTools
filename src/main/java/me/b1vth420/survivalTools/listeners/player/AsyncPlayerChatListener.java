package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.ChatManager;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.DataUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class AsyncPlayerChatListener implements Listener {
    private HashMap<UUID, Long> cooldown;

    public AsyncPlayerChatListener() {
        cooldown = new HashMap();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Messages m = Messages.getInst();

        if(p.hasPermission("survivalTools.bypassChat")) return;

        if(!ChatManager.chatEnabled && !p.hasPermission("ehcTools.bypassChat")) {
            p.sendMessage(m.getMessage("chatIsOffMessage"));
            e.setCancelled(true);
            return;
        }

        if(!cooldown.containsKey(p.getUniqueId())) {
            cooldown.put(p.getUniqueId(), DataUtil.parseDateDiff(ChatManager.cooldown, true));
            return;
        }

        if(cooldown.get(p.getUniqueId()) > System.currentTimeMillis()) {
            p.sendMessage(Messages.getInst().getMessage("chatCooldownPlayerMessage").replace("{TIME}", DataUtil.secondsToString(cooldown.get(p.getUniqueId()))));
            e.setCancelled(true);
            return;
        }

        cooldown.put(p.getUniqueId(), DataUtil.parseDateDiff(ChatManager.cooldown, true));

    }
}
