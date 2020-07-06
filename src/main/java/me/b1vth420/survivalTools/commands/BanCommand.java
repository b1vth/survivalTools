package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.objects.Ban;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand extends Command {
    public BanCommand() {
        super("ban", "Banowanie graczy", "survivalTools.ban", "/ban [nick] [powod] [czas]", false, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String name = String.join("", args[0]);
        String admin = (sender instanceof Player) ? sender.getName() : "Konsola";
        String reason = (args.length > 1) ? ChatUtil.chat(StringUtil.splitString(args, " ", 1)) : ChatUtil.chat("&cBrak");
        Messages m = Messages.getInst();

        OfflinePlayer p = null;

        OfflinePlayer[] players = Bukkit.getOfflinePlayers();

        for(int i = 0; i < players.length; i++) {
            if(players[i].getName().equals(name)) p = players[i];
        }

        if (p == null) {
            sender.sendMessage(m.getMessage("noSuchPlayerMessage"));
            return;
        }

        new Ban(p.getUniqueId(), p.getName(), admin, reason, 0L);

        if (p.isOnline()) {
            p.getPlayer().kickPlayer(ChatUtil.chat("&4Zostales zbanowany!" +
                    "\n&4Przez: &c" + admin +
                    "\n&4Powod: &c" + reason));
        }

        sender.sendMessage(m.getMessage("adminBanPlayerMessage").replace("{PLAYER_NAME}", p.getName()).replace("{REASON}", reason));
        Bukkit.broadcastMessage(m.getMessage("playerBannedMessage").replace("{PLAYER_NAME}", p.getName()).replace("{REASON}", reason).replace("{ADMIN}", admin));
    }
}