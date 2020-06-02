package me.b1vth420.survivalTools;

import me.b1vth420.survivalTools.commands.*;
import me.b1vth420.survivalTools.data.FileManager;
import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.listeners.entity.EntityDamageByEntityListener;
import me.b1vth420.survivalTools.listeners.player.*;
import me.b1vth420.survivalTools.managers.AntyLogoutManager;
import me.b1vth420.survivalTools.managers.TimerManager;
import me.b1vth420.survivalTools.tasks.AntyLogoutTask;
import me.b1vth420.survivalTools.tasks.HiddenMessageTask;
import me.b1vth420.survivalTools.utils.RegisterUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public HiddenMessageTask hiddenMessageTask;

    private static Main inst;

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
    }

    private void init() {
        new RegisterUtil();
        new FileManager();
        new Config(); //This could be moved to FileManager
        new Messages(); //This could be moved to FileManager
        hiddenMessageTask = new HiddenMessageTask(this);
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
    }

    private void registerTasks() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, hiddenMessageTask, 40L, 40L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AntyLogoutTask(), 20L, 20L);
    }

    public static Main getInst() {
        if(inst == null) return new Main();
        return inst;
    }
}
