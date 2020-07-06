package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand extends Command {

    public DayCommand() {
        super("day", "Ustawianie dnia", "survivalTools.day", "/day", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player p = (Player) sender;
        World w = p.getWorld();

        w.setTime(1000);
        sender.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("dayCommandMessage")));
    }
}
