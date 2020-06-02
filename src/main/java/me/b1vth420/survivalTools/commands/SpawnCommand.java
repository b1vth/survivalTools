package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends Command {

    public SpawnCommand() {
        super("spawn", "Teleportowanie sie na spawna!", "survivalTools.spawn", "/spawn", false, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Messages m = Messages.getInst();

        if(!(sender instanceof Player)) {
            if(args.length < 1) {
                sender.sendMessage(ChatUtil.chat("&4Blad! &cNie mozesz teleportowac sie na spawn!"));
                return;
            }
            Player p = Bukkit.getPlayer(String.join("", args[0]));

            if(p == null) {
                sender.sendMessage(m.getMessage("noSuchPlayerMessage"));
                return;
            }
            p.sendMessage(m.getMessage("spawnTeleportMessage"));
            p.teleport(p.getWorld().getSpawnLocation());
            return;
        }

        if(args.length > 0) {
            Player p = Bukkit.getPlayer(String.join("", args[0]));

            if(p == null) {
                sender.sendMessage(m.getMessage("noSuchPlayerMessage"));
                return;
            }
            p.sendMessage(m.getMessage("spawnTeleportMessage"));
            p.teleport(p.getWorld().getSpawnLocation());
            return;
        }

        Player p = (Player) sender;

        int teleportTime = Config.getInst().spawnTeleportTime;
        if(p.hasPermission("survivalTools.spawnBypass"))
            teleportTime = 0;

        TeleportUtil.teleport(p, p.getWorld().getSpawnLocation(), teleportTime,  "na spawna");
    }
}
