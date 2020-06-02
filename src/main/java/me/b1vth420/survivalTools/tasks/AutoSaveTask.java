package me.b1vth420.survivalTools.tasks;

import me.b1vth420.survivalTools.data.flat.DataManager;
import me.b1vth420.survivalTools.managers.UserManager;
import me.b1vth420.survivalTools.objects.User;
import org.bukkit.Bukkit;

public class AutoSaveTask implements Runnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach((p) -> {
            User u = UserManager.getUser(p);
            DataManager.saveUser(u);
        });
    }
}
