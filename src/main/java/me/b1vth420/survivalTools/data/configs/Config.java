package me.b1vth420.survivalTools.data.configs;

import me.b1vth420.survivalTools.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    private static Config inst;
    private FileConfiguration cfg;

    public Config(){
        inst = this;
        cfg = Main.getInst().getConfig();
        load();
    }

    public boolean repairNetherPortals;
    public boolean removeJoinQuitMessage;
    public int spawnTeleportTime;
    public String antylogoutTime;
    public List<String> blockedCommands;
    public List<String> logoutBlockedCommands;
    public int[] randomTeleportMin = new int[2];
    public int[] randomTeleportMax = new int[2];
    public Material randomTeleportBlock;
    public Material randomOnePersonTeleportButton;
    public Material randomMultiplePersonTeleportButton;
    public int randomMultiplePersonSearchDist;

    private void load() {
        this.repairNetherPortals = cfg.getBoolean("repairNetherPortals");
        this.removeJoinQuitMessage = cfg.getBoolean("removeJoinQuitMessage");
        this.spawnTeleportTime = cfg.getInt("spawnTeleportTime");
        this.antylogoutTime = cfg.getString("antylogoutTime");
        this.blockedCommands = cfg.getStringList("blockedCommands");
        this.logoutBlockedCommands = cfg.getStringList("logoutBlockedCommands");
        this.randomTeleportMin[0] = cfg.getInt("randomTeleport.min.x");
        this.randomTeleportMin[1] = cfg.getInt("randomTeleport.min.y");
        this.randomTeleportMax[0] = cfg.getInt("randomTeleport.max.x");
        this.randomTeleportMax[1] = cfg.getInt("randomTeleport.max.y");
        this.randomTeleportBlock = Material.matchMaterial(cfg.getString("randomTeleport.block"));
        this.randomOnePersonTeleportButton = Material.matchMaterial(cfg.getString("randomTeleport.onePersonButton"));
        this.randomMultiplePersonTeleportButton = Material.matchMaterial(cfg.getString("randomTeleport.multiplePersonButton"));
        this.randomMultiplePersonSearchDist = cfg.getInt("randomTeleport.multiplePersonDistance");
    }

    public static Config getInst(){
        return inst;
    }

}
