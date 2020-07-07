package me.b1vth420.survivalTools.data.mysql;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.managers.BanIPManager;
import me.b1vth420.survivalTools.managers.BanManager;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.Ban;
import me.b1vth420.survivalTools.objects.BanIP;
import me.b1vth420.survivalTools.objects.User;
import me.b1vth420.survivalTools.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MySQL {

    private static MySQL inst;

    public MySQL() {
        inst = this;
        createTables();
    }

    public static void createTables() {
        Main.getInst().getSQLManager().createTable("CREATE TABLE IF NOT EXISTS tools_Users(UUID VARCHAR(36) NOT NULL, name VARCHAR(16) NOT NULL, ip TEXT NOT NULL, firstJoin BIGINT(19) NOT NULL, lastJoin BIGINT(19) NOT NULL, msgEnabled TEXT NOT NULL, PRIMARY KEY(UUID))");
        Main.getInst().getSQLManager().createTable("CREATE TABLE IF NOT EXISTS tools_Bans(UUID VARCHAR(36) NOT NULL, name VARCHAR(16) NOT NULL, admin TEXT NOT NULL, reason TEXT NOT NULL, time BIGINT(19) NOT NULL, PRIMARY KEY(UUID))");
        Main.getInst().getSQLManager().createTable("CREATE TABLE IF NOT EXISTS tools_BansIP(UUID VARCHAR(36) NOT NULL, IP  TEXT NOT NULL, name VARCHAR(16) NOT NULL, admin TEXT NOT NULL, reason TEXT NOT NULL, time BIGINT(19) NOT NULL, PRIMARY KEY(UUID))");
        Main.getInst().getSQLManager().createTable("CREATE TABLE IF NOT EXISTS tools_DetectorBlocks(UUID VARCHAR(36) NOT NULL, name VARCHAR(16) NOT NULL, time TEXT not null, block TEXT NOT NULL, amount INTEGER NOT NULL)");
    }

    public void loadAll() {
        loadUsers();
        loadBans();
    }

    public void saveAll() {
        for(User u : UserManager.getUsers().values()) u.update();
        for(Ban b : BanManager.getBans().values()) b.update();
        for(BanIP b: BanIPManager.getBans().values()) b.update();
    }

    public void insertDetectorBlock(Block b, int amount , Player p) {
        String location = StringUtil.locationToString(b.getLocation());
        String uuid = p.getUniqueId().toString();
        String name = p.getName();

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = myDateObj.format(myFormatObj);
        if(amount == 0) amount++;
        //Async to not block main thread
        int finalAmount = amount;
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInst(), () -> {

            Main.getInst().getSQLManager().executeUpdate("INSERT INTO tools_DetectorBlocks(UUID, name, time, block, amount) VALUES (?,?,?,?,?)", new String[] {
                uuid, name, date, location, String.valueOf(finalAmount)
            });
        });
    }


    private void loadUsers() {
        for(Map<String, Object> users : Main.getInst().getSQLManager().executeQuery("SELECT * FROM tools_Users")) {
            new User(users);
        }
    }

    private void loadBans() {
        for(Map<String, Object> bans : Main.getInst().getSQLManager().executeQuery("SELECT * FROM tools_Bans")) {
            new Ban(bans);
        }

        for(Map<String, Object> bans : Main.getInst().getSQLManager().executeQuery("SELECT * FROM tools_BansIP")) {
            new BanIP(bans);
        }
    }

    public static MySQL getInst() {
        if(inst == null) return new MySQL();
        return inst;
    }
}
