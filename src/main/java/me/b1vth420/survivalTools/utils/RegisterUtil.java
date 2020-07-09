package me.b1vth420.survivalTools.utils;

import com.comphenix.protocol.utility.MinecraftReflection;
import me.b1vth420.survivalTools.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RegisterUtil {

  private static final String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
  @SuppressWarnings("rawtypes")
  private static Class craftServerClass;
  private static SimpleCommandMap commandMap;

  static {
    try {
      craftServerClass = Class.forName("org.bukkit.craftbukkit." + bukkitVersion + ".CraftServer");
      Field f = craftServerClass.getDeclaredField("commandMap");
      f.setAccessible(true);
      commandMap = (SimpleCommandMap) f.get(craftServerClass.cast(Main.getInst().getServer()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void registerCommand(Command cmd) {
    commandMap.register(cmd.getName(), cmd);
  }

  public SimpleCommandMap getCommandMap() {
    return commandMap;
  }

  public static Class<?> getClassFromName(String name) {
    Class c = null;
    try {
       c = Class.forName("org.bukkit.craftbukkit." + bukkitVersion + "." + name);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return c;
  }

  public static Class<?> getNMSClass(String name) {
    Class c = null;
    try {
      c = Class.forName("net.minecraft.server." + bukkitVersion + "." + name);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return c;
  }

  public static int getPing(Player player) {
    try {
      Field pingField = MinecraftReflection.getEntityPlayerClass().getField("ping");
      Method getHandle = player.getClass().getSuperclass().getMethod("getHandle");
      return (int) pingField.get(getHandle.invoke(player));
    } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return 0;
  }

}

