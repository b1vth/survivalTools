package me.b1vth420.survivalTools;

import me.b1vth420.survivalTools.commands.*;
import me.b1vth420.survivalTools.data.DataSaveType;
import me.b1vth420.survivalTools.data.FileManager;
import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.data.flat.DataManager;
import me.b1vth420.survivalTools.data.mysql.MySQL;
import me.b1vth420.survivalTools.data.mysql.SQLManager;
import me.b1vth420.survivalTools.listeners.entity.EntityDamageByEntityListener;
import me.b1vth420.survivalTools.listeners.entity.EntityExplodeListener;
import me.b1vth420.survivalTools.listeners.player.*;
import me.b1vth420.survivalTools.managers.AntyLogoutManager;
import me.b1vth420.survivalTools.managers.TimerManager;
import me.b1vth420.survivalTools.tasks.AntyLogoutTask;
import me.b1vth420.survivalTools.tasks.AutoMessageTask;
import me.b1vth420.survivalTools.tasks.AutoSaveTask;
import me.b1vth420.survivalTools.tasks.HiddenMessageTask;
import me.b1vth420.survivalTools.utils.RegisterUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public HiddenMessageTask hiddenMessageTask;

    private static Main inst;
    private static MySQL mysql;

    private SQLManager sql;
    private FileManager fileManager;

    public Main() {
        inst = this;
    }

    @Override
    public void onEnable() {
        init();
        registerCommands();
        registerListeners();
        registerTasks();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach((p) -> AntyLogoutManager.removeCombat(p.getUniqueId()));
        if(Config.getInst().dataSaveType == DataSaveType.FLAT) DataManager.saveAll();
        if(Config.getInst().dataSaveType == DataSaveType.MYSQL) {
            MySQL.getInst().saveAll();
            sql.onDisable();
        }
    }

    private void init() {
        new RegisterUtil();
        this.fileManager = new FileManager();
        new Config(); //This could be moved to FileManager
        fileManager.checkFlat();
        new Messages(); //This could be moved to FileManager
        hiddenMessageTask = new HiddenMessageTask(this);
       if(Config.getInst().dataSaveType == DataSaveType.FLAT) DataManager.loadAll();
       if(Config.getInst().dataSaveType == DataSaveType.MYSQL) {
           registerDatabase();
           mysql = new MySQL();
           MySQL.getInst().loadAll();
       }
    }


    private void registerCommands() {
        RegisterUtil.registerCommand(new SetSpawnCommand());
        RegisterUtil.registerCommand(new InvseeCommand());
        RegisterUtil.registerCommand(new GameModeCommand());
        RegisterUtil.registerCommand(new VanishCommand(this));
        RegisterUtil.registerCommand(new KickCommand());
        RegisterUtil.registerCommand(new TeleportCommand());
        RegisterUtil.registerCommand(new BanCommand());
        RegisterUtil.registerCommand(new BanIPCommand());
        RegisterUtil.registerCommand(new UnbanCommand());
        RegisterUtil.registerCommand(new UnbanIPCommand());
        RegisterUtil.registerCommand(new ChatCommand());
        RegisterUtil.registerCommand(new SpawnCommand());
        RegisterUtil.registerCommand(new MsgCommand());
        RegisterUtil.registerCommand(new ReplyCommand());
        RegisterUtil.registerCommand(new WyjebaneCommand());
        RegisterUtil.registerCommand(new TempBanCommand());
        RegisterUtil.registerCommand(new DayCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPortalListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimerManager(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreProcessListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), this);
    }

    private void registerTasks() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, hiddenMessageTask, 40L, 40L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AntyLogoutTask(), 20L, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AutoSaveTask(), 12000L, 12000L);
        if(Config.getInst().autoMessagesEnabled) new AutoMessageTask(this);
    }

    public static Main getInst() {
        if(inst == null) return new Main();
        return inst;
    }

    private void registerDatabase(){
        sql = new SQLManager(this);
    }
    public SQLManager getSQLManager() { return this.sql; }
}
