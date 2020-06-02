package me.b1vth420.survivalTools.objects;

import me.b1vth420.survivalTools.data.configs.Config;
import me.b1vth420.survivalTools.utils.DataUtil;

import java.util.UUID;

public class Combat {

    private UUID p;
    private UUID attacker;
    private long lastAttackTime;

    public Combat(UUID p, UUID attacker) {
        this.p = p;
        this.attacker = attacker;
        this.lastAttackTime = DataUtil.parseDateDiff(Config.getInst().antylogoutTime, true);
    }

    public UUID getPlayer() { return p;}

    public UUID getAttacker() { return attacker; }

    public long getLastAttackTime() {return lastAttackTime; }
}
