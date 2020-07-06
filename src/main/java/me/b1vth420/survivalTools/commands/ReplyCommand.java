package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.User;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand extends Command {

    public ReplyCommand() {
        super("reply", "Szybkie odpowiadanie na prywatne wiadomosci", "survivalTools.reply", "/r <wiadomosc>", true, 1, new String[]{"r"});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Messages m = Messages.getInst();

        String message = StringUtil.splitString(args, " ", 0);

        if (MsgCommand.getLastMsg().get(p) == null) {
            p.sendMessage(m.getMessage("privateNoPersonToReply"));
            return;
        }

        Player to = Bukkit.getPlayer(MsgCommand.getLastMsg().get(p));

        if (to == null) {
            p.sendMessage(m.getMessage("noSuchPlayerMessage"));
            return;
        }

        User userTo = UserManager.getUser(to);
        if(!userTo.isMsg()) {
            sender.sendMessage(ChatUtil.chat("&4Blad! &cTen gracz ma wyjebane!"));
        }

        sender.sendMessage(m.getMessage("privateFromMessage").replace("{TO_NAME}", to.getName()).replace("{MESSAGE}", message));
        to.sendMessage(m.getMessage("privateToMessage").replace("{FROM_NAME}", sender.getName()).replace("{MESSAGE}", message));
    }
}
