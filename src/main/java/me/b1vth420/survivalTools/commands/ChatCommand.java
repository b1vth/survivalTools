package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand extends Command {
    public ChatCommand() {
        super("chat", "Zarzadzanie chatem", "survivalTools.chat", "/chat [clear/on/off/cooldown]", false, 1, new String[0]);
    }

    Messages m = Messages.getInst();

    @Override
    public void execute(CommandSender sender, String[] args) {

        switch (String.join("", args[0])) {
            case "clear": chatClear(sender); break;
            case "on": chatOn(sender); break;
            case "off": chatOff(sender); break;
            case "cooldown" :  cooldown(sender, String.join("", args[1])); break;
            default: break;
        }
    }

    void cooldown(CommandSender sender, String s) {
        ChatManager.cooldown = s;
        for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage(m.getMessage("chatCooldownMessage").replace("{PLAYER_NAME}", sender.getName()).replace("{TIME}", s));
    }

    void chatClear(CommandSender sender) {
        for(int i = 0; i < 100; i++) {
            for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage("");
        }
        for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage(m.getMessage("chatClearMessage").replace("{PLAYER_NAME}", sender.getName()));
    }

    void chatOn(CommandSender sender) {
        ChatManager.chatEnabled = true;
        for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage(m.getMessage("chatOnMessage").replace("{PLAYER_NAME}", sender.getName()));
    }

    void chatOff(CommandSender sender) {
        ChatManager.chatEnabled = false;
        for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage(m.getMessage("chatOffMessage").replace("{PLAYER_NAME}", sender.getName()));
    }
}