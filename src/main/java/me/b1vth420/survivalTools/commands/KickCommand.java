package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand extends Command {
    public KickCommand() {
        super("kick", "Wyrzucanie graczy", "survivalTools.kick", "/kick <gracz> <powod>", false, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Messages m = Messages.getInst();

        String reason = "Brak";
        String playerName = "";
        if(args.length >= 1) playerName = String.join("", args[0]);

        if(playerName.isEmpty()) {
            sender.sendMessage(ChatUtil.chat(m.getMessage("errorMessage")));
            return;
        }

        if(args.length > 1) reason = StringUtil.splitString(args, " ", 1);

        Player p = Bukkit.getPlayer(playerName);

        if(p == null) {
            sender.sendMessage(m.getMessage("noSuchPlayerMessage"));
            return;
        }

        if(p.getName().equals(sender.getName())) {
            p.sendMessage("&4Blad! &cNie mozesz wyrzucic siebie!");
            return;
        }

        p.kickPlayer(ChatUtil.chat("&4Zostales wyrzucony!" +
                "\n&4Przez: &c" + sender.getName() +
                "\n&4Powod: &c" + reason));
    }
}
