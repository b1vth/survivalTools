package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.tasks.HiddenMessageTask;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand extends Command {

    private Main inst;
    private HiddenMessageTask hiddenMessageTask;

    public VanishCommand(Main inst) {
        super("vanish", "Ukrywanie siebie/gracza", "survivalTools.vanish", "/vanish [nick]", false, 0, new String[] { "v"});
        this.inst = inst;
        this.hiddenMessageTask = inst.hiddenMessageTask;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String playerName = null;
        Messages m = Messages.getInst();

        if(sender instanceof Player) playerName = sender.getName();

        if(args.length == 1) playerName = String.join("", args[0]);

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
            sender.sendMessage(ChatUtil.chat("&4Blad! Nie mozesz stac sie niewidzialny!"));
            return;
        }

        if(p.getName().equals(sender.getName())) {
            if(!hiddenMessageTask.isHidden(p)) {
                hiddenMessageTask.addHiddenPlayer(p);
                return;
            }
            hiddenMessageTask.removeHiddenPlayer(p);
            return;
        }

        if(!hiddenMessageTask.isHidden(p)) {
            hiddenMessageTask.addHiddenPlayer(p);
            sender.sendMessage(m.getMessage("vanishPlayerChangeMessage").replace("{MODE}", ChatUtil.chat("&aniewidzialny")).replace("{PLAYER_NAME}", p.getName()));
            return;
        }
        hiddenMessageTask.removeHiddenPlayer(p);
        sender.sendMessage(m.getMessage("vanishPlayerChangeMessage").replace("{MODE}", ChatUtil.chat("&cwidzialny")).replace("{PLAYER_NAME}", p.getName()));
        return;
    }
}
