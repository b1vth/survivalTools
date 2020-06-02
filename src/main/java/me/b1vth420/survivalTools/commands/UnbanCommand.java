package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.BanManager;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.Ban;
import me.b1vth420.survivalTools.objects.User;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.command.CommandSender;

public class UnbanCommand extends Command {

    public UnbanCommand() {
        super("unban", "Odblokowanie gracza", "survivalTools.unban", "/unban <nick>", false, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        User u = UserManager.getUser(String.join("", args[0]));
        Messages m = Messages.getInst();
        if (u == null) {
            sender.sendMessage(m.getMessage("noSuchPlayerMessage"));
            return;
        }

        Ban b = BanManager.getBan(u.getUuid());

        if (b == null) {
            sender.sendMessage(m.getMessage("playerIsAlreadyUnbannedMessage"));
            return;
        }

        sender.sendMessage(m.getMessage("playerUnbannedMessage").replace("{PLAYER_NAME}", u.getName()));
        BanManager.removeBan(b);
    }
}