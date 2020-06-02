package me.b1vth420.survivalTools.utils;

import me.b1vth420.survivalTools.data.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomTeleportUtil {

    static List<Biome> blockedBiomes = Arrays.asList(Biome.OCEAN, Biome.COLD_OCEAN, Biome.DEEP_COLD_OCEAN, Biome.DEEP_FROZEN_OCEAN,
    Biome.DEEP_LUKEWARM_OCEAN, Biome.DEEP_OCEAN, Biome.FROZEN_OCEAN, Biome.WARM_OCEAN, Biome.LUKEWARM_OCEAN, Biome.DEEP_WARM_OCEAN);

    public static Location getSafeLocation(World w) {
        int randomCords[] = new int[3];

        randomCords[0] = RandomUtil.getRandInt(Config.getInst().randomTeleportMin[0], Config.getInst().randomTeleportMax[0]);
        randomCords[2] = RandomUtil.getRandInt(Config.getInst().randomTeleportMin[1], Config.getInst().randomTeleportMax[1]);
        randomCords[1] = w.getHighestBlockYAt(randomCords[0], randomCords[2]) + 2;

        Location loc = new Location(w, randomCords[0], randomCords[1], randomCords[2]);
        if(blockedBiomes.contains(w.getBiome(loc.getBlockX(), loc.getBlockZ()))) {
            return getSafeLocation(w);
        }
        return loc;
    }

    public static List<Player> getPlayersOnPressurePlate(int dist, Block b) {
        List<Player> toReturn = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(b.getLocation().distance(p.getLocation()) <= dist) {
                if(p.getLocation().getBlock().getType() == Config.getInst().randomMultiplePlateType) toReturn.add(p);
            }
        }
        return toReturn;
    }
}
