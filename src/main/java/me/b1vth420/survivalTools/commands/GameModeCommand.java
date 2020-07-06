package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand  extends Command {
    public GameModeCommand() {
        super("gamemode", "Zmiana trybu gry", "survivalTools.gamemode", "/gamemode <tryb> <gracz>", false, 1, new String[] {"gm"});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String playerName = null;
        GameMode gameMode = StringUtil.gameModeFromString(String.join("", args[0]));
        Messages m = Messages.getInst();

        if(sender instanceof Player) playerName = sender.getName();

        if(args.length == 2) playerName = String.join("", args[1]);

        if(playerName == null) {
            sender.sendMessage(ChatUtil.chat(m.getMessage("errorMessage")));
            return;
        }
        Player p = Bukkit.getPlayer(playerName);

        if(p == null) {
            p.sendMessage(m.getMessage("noSuchPlayerMessage"));
            return;
        }

        if(args.length == 0 && !(sender instanceof Player)) {
            sender.sendMessage(ChatUtil.chat("&4Blad! Nie mozesz zmienic swojego trybu gry!"));
            return;
        }

        if(p.getName().equals(sender.getName())) {
            p.sendMessage(m.getMessage("gamemodeChangeMessage").replace("{GAMEMODE}", gameMode.name()));
            p.setGameMode(gameMode);
            return;
        }

        p.sendMessage(m.getMessage("gamemodeChangeMessage").replace("{GAMEMODE}", gameMode.name()));
        sender.sendMessage(m.getMessage("gamemodePlayerChangeMessage").replace("{GAMEMODE}", gameMode.name()).replace("{PLAYER_NAME}", p.getName()));
        p.setGameMode(gameMode);
    }
}