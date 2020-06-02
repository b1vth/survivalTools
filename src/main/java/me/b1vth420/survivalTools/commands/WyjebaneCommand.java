package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WyjebaneCommand extends Command {

    public WyjebaneCommand() {
        super("wyjebane", "Wylaczenie msg", "survivalTools.wyjebane", "/wyjebane", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User u = UserManager.getUser(p);

        if(u.isMsg()) {
            p.sendMessage(Messages.getInst().getMessage("wyjebaneOffMessage"));
            u.changeMsg();
            return;
        }
        p.sendMessage(Messages.getInst().getMessage("wyjebaneOnMessage"));
        u.changeMsg();
    }
}
