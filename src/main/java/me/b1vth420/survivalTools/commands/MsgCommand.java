package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.User;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class MsgCommand extends Command {

    private static final HashMap<UUID, UUID> lastMsg = new HashMap<>();

    public MsgCommand() {
        super("msg", "Pisanie prywatnych wiadomosci", "ehcTools.msg", "/msg <nick> <wiamdomosc>", false, 2, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player to = Bukkit.getPlayer(args[0]);
        String message = StringUtil.splitString(args, " ", 1);
        Messages m = Messages.getInst();

        if(to == null) {
            sender.sendMessage(m.getMessage("noSuchPlayerMessage"));
            return;
        }

        if(sender.getName().equals(to.getName())) {
            sender.sendMessage(m.getMessage("privateSelfMessage"));
            return;
        }

        User userTo = UserManager.getUser(to);
        if(!userTo.isMsg()) {
            sender.sendMessage(ChatUtil.chat("&4Blad! &cTen gracz ma wyjebane!"));
        }

        sender.sendMessage(m.getMessage("privateFromMessage").replace("{TO_NAME}", to.getName()).replace("{MESSAGE}", message));
        to.sendMessage(m.getMessage("privateToMessage").replace("{FROM_NAME}", sender.getName()).replace("{MESSAGE}", message));

        if(sender instanceof Player) {
            lastMsg.put(((Player) sender).getUniqueId(), to.getUniqueId());
            lastMsg.put((to.getUniqueId()), (((Player) sender).getUniqueId()));
        }
    }

    public static HashMap<UUID, UUID> getLastMsg() {
        return lastMsg;
    }
}
