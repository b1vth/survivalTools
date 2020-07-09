package me.b1vth420.survivalTools.tasks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import me.b1vth420.survivalTools.Main;
import me.b1vth420.survivalTools.data.configs.Messages;
import me.b1vth420.survivalTools.utils.ChatUtil;
import me.b1vth420.survivalTools.utils.RegisterUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
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
        p.sendMessage(Messages.getInst().getMessage("vanishChangeMessage").replace("{MODE}", ChatUtil.chat("&aniewidzialny")));
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.chat("&aJestes niewidzialny")));

        PacketContainer playerInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        playerInfo.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);

        PlayerInfoData pid = new PlayerInfoData(WrappedGameProfile.fromPlayer(p), RegisterUtil.getPing(p), EnumWrappers.NativeGameMode.fromBukkit(p.getGameMode()), WrappedChatComponent.fromText(p.getPlayerListName()));
        playerInfo.getPlayerInfoDataLists().write(0, Collections.singletonList(pid));

        for(Player px : Bukkit.getOnlinePlayers()) {
            try {
                if(!px.hasPermission("survivalTools.vanishSee")) px.hidePlayer(inst, p);
                ProtocolLibrary.getProtocolManager().sendServerPacket(px, playerInfo);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeHiddenPlayer(Player p) {
        hiddenPlayers.remove(p.getUniqueId());
        p.sendMessage(Messages.getInst().getMessage("vanishChangeMessage").replace("{MODE}", ChatUtil.chat("&cwidzialny")));
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.chat("&cJestes widzialny")));

        PacketContainer playerInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        playerInfo.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        PlayerInfoData pid = new PlayerInfoData(WrappedGameProfile.fromPlayer(p), RegisterUtil.getPing(p), EnumWrappers.NativeGameMode.fromBukkit(p.getGameMode()), WrappedChatComponent.fromText(p.getPlayerListName()));
        playerInfo.getPlayerInfoDataLists().write(0, Collections.singletonList(pid));

        for(Player px : Bukkit.getOnlinePlayers()) {
            try {
                px.showPlayer(inst, p);
                ProtocolLibrary.getProtocolManager().sendServerPacket(px, playerInfo);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isHidden(Player p) {
        return hiddenPlayers.contains(p.getUniqueId());
    }
}
