package me.b1vth420.survivalTools.commands;

import org.bukkit.command.CommandSender;

public class WeatherCommand extends Command {

    public WeatherCommand() {
        super("weather", "Ustawianie pogody", "survivalTools.weather", "/weather <sun/rain/thunder>", true, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
