package me.b1vth420.survivalTools.commands;

import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeatherCommand extends Command {

    public WeatherCommand() {
        super("weather", "Ustawianie pogody", "survivalTools.weather", "/weather <sun/rain/thunder>", true, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        switch(String.join("", args[0])) {
            case "sun":
                p.getWorld().setStorm(false);
                p.getWorld().setThundering(false);
                break;
            case "rain":
                p.getWorld().setStorm(true);
                break;
            case "thunder":
                p.getWorld().setStorm(true);
                p.getWorld().setThundering(true);
                break;
            default:
                p.sendMessage(this.usageMessage);
        }

        p.sendMessage(ChatUtil.chat(Messages.getInst().getMessage("")));
    }
}
