package me.b1vth420.survivalTools.tasks;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.utils.RandomUtil;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class AutoMessageTask implements Runnable {

    private HashMap<Integer, String> autoMessage = new HashMap<>();
    private Main inst;

    public AutoMessageTask(Main inst) {
        this.inst = inst;

        for(int i = 0; i < Config.getInst().autoMessages.size(); i++) {
            this.autoMessage.put(i, Config.getInst().autoMessages.get(i));
        }

        inst.getServer().getScheduler().runTaskTimerAsynchronously(inst, this, 20L, 600L);
    }

    @Override
    public void run() {
        inst.getServer().broadcastMessage(autoMessage.get(RandomUtil.getRandInt(0, autoMessage.size()-1))); //Wybiera randomowa wadomosc
    }
}
