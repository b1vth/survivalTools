package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends Command {

    public TeleportCommand() {
        super("tp", "Teleportowanie graczy", "survivalTools.teleport", "/teleport [kto] [do kogo]", false, 1, new String[] { "teleport"});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Messages m = Messages.getInst();

        if(args.length == 1) {
            Player p = (Player) sender;
            Player p2 = Bukkit.getPlayer(args[0]);

            if(p2 == null) {
                p.sendMessage(m.getMessage("noSuchPlayerMessage"));
                return;
            }

            p.sendMessage(m.getMessage("teleportMessage").replace("{PLAYER_NAME}", p2.getName()));
            p.teleport(p2);
        }
        if(args.length == 2) {
            Player p = Bukkit.getPlayer(args[0]);
            Player p2 = Bukkit.getPlayer(args[1]);
            if(p2 == null ) {
                p.sendMessage(m.getMessage("noSuchPlayerMessage"));
                return;
            }

            p.sendMessage(m.getMessage("teleportToMessage").replace("{PLAYER_NAME}", p2.getName()));
            p.teleport(p2);
        }
    }
}
