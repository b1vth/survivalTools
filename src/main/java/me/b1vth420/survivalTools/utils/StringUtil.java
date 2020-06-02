package me.b1vth420.survivalTools.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static Location stringToLocation(String s ) {
        String[] ss = s.split(";");
        return new Location(Bukkit.getWorld(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]), Integer.parseInt(ss[3]));
    }

    public static String locationToString(Location l) {
        return l.getWorld().getName() + ";" + l.getBlockX() + ";" + l.getBlockY() + ";" + l.getBlockZ();
    }

    public static String splitString(String toSplit, String splitter) {
        StringBuilder toReturn = new StringBuilder();
        for(String s : toSplit.split(splitter)) toReturn.append(s + " ");
        return toReturn.toString();
    }

    public static String splitString(String[] toSplit, String splitter, int ignore) {
        StringBuilder toReturn = new StringBuilder();
        for(int i = ignore; i < toSplit.length; i++) toReturn.append(toSplit[i] + " ");
        return toReturn.toString();
    }

    public static GameMode gameModeFromString(String s){
        if(ChatUtil.isInteger(s)) {
            switch (Integer.parseInt(s)) {
                case 0: return GameMode.SURVIVAL;
                case 1: return GameMode.CREATIVE;
                case 2: return GameMode.ADVENTURE;
                case 3: return GameMode.SPECTATOR;
                default: return GameMode.SURVIVAL;
            }
        }
        switch (s.toLowerCase()) {
            case "survival": return GameMode.SURVIVAL;
            case "creative": return GameMode.CREATIVE;
            case "adventure": return GameMode.ADVENTURE;
            case "spectator": return GameMode.SPECTATOR;
            default: return GameMode.SURVIVAL;
        }
    }

    public static List<Material> materialFromStringList(List<String> list) {
        List<Material> toReturn = new ArrayList<>();
        list.forEach((s) -> toReturn.add(Material.matchMaterial(s.toUpperCase())));
        return toReturn;
    }
}
