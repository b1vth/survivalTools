package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand extends Command {
    public InvseeCommand() {
        super("invsee", "Otwieranie inventory graczy", "survivalTools.invsee", "/invsee [nick]", true, 1, new String[] {"inventory"});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Player toOpen = Bukkit.getPlayer(String.join("", args[0]));

        if(toOpen == null) {
            p.sendMessage(ChatUtil.chat("&4Blad! &cTakiego gracza nie ma na serwerze!"));
            return;
        }
        
        p.sendMessage(Messages.getInst().getMessage("invseeMessage").replace("{PLAYER}", p.getName()));
        p.openInventory(toOpen.getInventory());
    }
}
