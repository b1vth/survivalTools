package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class GodCommand extends Command {

    public HashSet<UUID> gods = new HashSet<>();

    public GodCommand() {
        super("god", "No kurwa god", "survialTools.god", "/god <nick>", false, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p;

        if(args.length == 1) {
            p = Bukkit.getPlayer(String.join("", args[0]));

            if(p == null) {
                sender.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("noSuchPlayerMessage")));
                return;
            }

            if (gods.contains(p.getUniqueId())) {
                gods.remove(p.getUniqueId());
            } else {
                gods.add(p.getUniqueId());
            }

            p.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("godPlayerMessage").replace("{MODE}", gods.contains(p.getUniqueId()) ? "&cWylaczony": "&aWlaczony")));
            sender.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("godSenderMessage").replace("{MODE}", gods.contains(p.getUniqueId()) ? "&cWylaczony": "&aWlaczony")));

            return;
        }

        if(sender instanceof Player) {

        }
    }
}
