package me.b1vth420.survivalTools.listeners.player;

import me.b1vth420.survivalTools.managers.BanIPManager;
import me.b1vth420.survivalTools.managers.BanManager;
import me.b1vth420.survivalTools.objects.Ban;
import me.b1vth420.survivalTools.objects.BanIP;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLogin(AsyncPlayerPreLoginEvent e) {
        String p = e.getName();
        Ban b = BanManager.getBan(p);
        if (b != null) {
            if (b.getTime() == 0L) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
                        ChatUtil.chat("&4Zostales zbanowany!" +
                                "\n&4Przez: &c" + b.getAdmin() +
                                "\n&4Powod: &c" + b.getReason()));
                return;
            }
        }
        String ip = e.getAddress().getHostAddress().replace("/", "");
        BanIP ban = BanIPManager.getBan(ip);
        if(ban != null) {
            if(ban.getTime() == 0L) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
                        ChatUtil.chat("&4Zostales zbanowany!" +
                        "\n&4Przez: &c" + ban.getAdmin() +
                        "\n&4Powod: &c" + ban.getReason()));
            }
        }
    }
}
