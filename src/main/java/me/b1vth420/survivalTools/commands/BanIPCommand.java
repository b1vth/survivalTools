package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.BanIP;
import me.b1vth420.survivalTools.objects.User;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanIPCommand extends Command {
    public BanIPCommand() {
        super("banip", "Blokowanie  ip", "survivalTools.banip", "/banip [nick] [powod]", false, 1, new  String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String name = String.join("", args[0]);
        String admin = (sender instanceof Player) ? sender.getName() : "Konsola";
        String reason = (args.length > 1) ? ChatUtil.chat(StringUtil.splitString(args, " ", 1)) : ChatUtil.chat("&cBrak");
        User u = UserManager.getUser(name);
        Messages m = Messages.getInst();

        if(u == null) {
            sender.sendMessage(m.getMessage("noSuchPlayerMessage"));
            return;
        }

        new BanIP(u.getUuid(), u.getIp(), u.getName(), admin, reason, 0L);

        Player p = Bukkit.getPlayer(u.getUuid());
        if(p != null) {
            p.kickPlayer(ChatUtil.chat("&4Zostales zbanowany!" +
                    "\n&4Przez: &c" + admin +
                    "\n&4Powod: &c" + reason));
        }

        sender.sendMessage(m.getMessage("adminBanPlayerMessage").replace("{PLAYER_NAME}", u.getName()).replace("{REASON}", reason));
        Bukkit.broadcastMessage(m.getMessage("playerBannedMessage").replace("{PLAYER_NAME}", u.getName()).replace("{REASON}", reason).replace("{ADMIN}",admin));
    }
}