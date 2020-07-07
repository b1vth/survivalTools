package me.b1vth420.survivalTools.tasks;

import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class HiddenMessageTask implements Runnable {

    private Main inst;

    public HiddenMessageTask(Main inst) {
        this.inst = inst;
    }

    private static HashSet<UUID> hiddenPlayers = new HashSet<>();

    @Override
    public void run() {
        for(UUID uuid : hiddenPlayers) {
            Player p = Bukkit.getPlayer(uuid);
            if(p == null) {
                hiddenPlayers.remove(uuid);
                continue;
            }
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.chat("&aJestes niewidoczny")));
        }
    }

    public void addHiddenPlayer(Player p) {
        hiddenPlayers.add(p.getUniqueId());
        p.hidePlayer(inst, p);
        p.sendMessage(Messages.getInst().getMessage("vanishChangeMessage").replace("{MODE}", ChatUtil.chat("&aniewidzialny")));
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.chat("&aJestes niewidzialny")));
        PacketPlayOutPlayerInfo info = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)p).getHandle());

        for(Player px : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)px).getHandle().playerConnection.sendPacket(info);
        }
    }

    public void removeHiddenPlayer(Player p) {
        hiddenPlayers.remove(p.getUniqueId());
        p.showPlayer(inst, p);
        p.sendMessage(Messages.getInst().getMessage("vanishChangeMessage").replace("{MODE}", ChatUtil.chat("&cwidzialny")));
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.chat("&cJestes widzialny")));

        PacketPlayOutPlayerInfo info = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)p).getHandle());

        for(Player px : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)px).getHandle().playerConnection.sendPacket(info);
        }
    }

    public boolean isHidden(Player p) {
        return hiddenPlayers.contains(p.getUniqueId());
    }
}
