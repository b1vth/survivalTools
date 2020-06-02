package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends Command {

    public SetSpawnCommand() {
        super("setspawn", "Ustawianie spawna", "survivalTools.setspawn", "/setspawn", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        p.getWorld().setSpawnLocation(p.getLocation());
        p.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("setSpawnMessage")));
    }
}
