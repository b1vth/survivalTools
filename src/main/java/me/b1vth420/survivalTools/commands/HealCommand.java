package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand extends Command {
    public HealCommand() {
        super("heal", "Leczenie gracza", "survivalTools.heal", "/heal <nick>", false, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1) {

            Player p = Bukkit.getPlayer(String.join("", args[0]));

            if (p == null) {
                p.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("noSuchPlayerMessage")));
                return;
            }

            p.setHealth(20);
            p.setFireTicks(0);
            p.setFoodLevel(20);

            sender.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("healSenderMessage").replace("{nick}", p.getName())));
            p.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("healPlayerMessage").replace("{nick}", sender.getName())));

            return;
        }

        Player p = (Player) sender;
        p.setHealth(20);
        p.setFireTicks(0);
        p.setFoodLevel(20);
        p.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("healSelfMessage")));
    }
}
