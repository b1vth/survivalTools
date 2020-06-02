package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.BanIPManager;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.BanIP;
import me.b1vth420.survivalTools.objects.User;
import org.bukkit.command.CommandSender;

public class UnbanIPCommand extends Command{
    public UnbanIPCommand() {
        super("unbanip", "Odblokowywanie ip gracza","ehcTools.unbanip", "/unbanip <nick>", false, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        User u = UserManager.getUser(String.join("", args[0]));
        Messages m = Messages.getInst();
        if(u == null) {
            sender.sendMessage(m.getMessage("noSuchPlayerMessage"));
            return;
        }

        BanIP b = BanIPManager.getBan(u.getIp());

        if(b == null) {
            sender.sendMessage(m.getMessage("playerIsAlreadyUnbannedMessage").replace("{PLAYER_NAME}", u.getName()));
            return;
        }

        sender.sendMessage(m.getMessage("playerUnbannedMessage").replace("{PLAYER_NAME}", u.getName()));
        BanIPManager.removeBan(b);
    }
}
