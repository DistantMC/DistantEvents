package me.nicbo.InvadedLandsEvents.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scoreboard.Objective;
import org.bukkit.util.BlockVector;

import java.util.*;

public final class ConfigUtils {
    private ConfigUtils() {}

    public static void serializeLoc(Location loc, boolean includeWorld, ConfigurationSection section) {
        if (includeWorld) section.set("world", loc.getWorld().getName());
        section.set("x", loc.getX());
        section.set("y", loc.getY());
        section.set("z", loc.getZ());
        section.set("yaw", loc.getYaw());
        section.set("pitch", loc.getPitch());
    }

    public static Location deserializeLoc(ConfigurationSection section, World world) {
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location deserializeLoc(ConfigurationSection section) {
        World world = Bukkit.getWorld(section.get("world").toString());
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static void blockVectorToConfig(BlockVector blockVector, ConfigurationSection section) {
        section.set("x", blockVector.getX());
        section.set("y", blockVector.getY());
        section.set("z", blockVector.getZ());
    }

    public static BlockVector blockVectorFromConfig(ConfigurationSection section) {
        int x = section.getInt("x");
        int y = section.getInt("y");
        int z = section.getInt("z");
        return new BlockVector(x, y, z);
    }

    public static String[] getConfigMessage(ConfigurationSection section) {
        List<String> message = new ArrayList<>();
        Map<String, Object> keyValues = section.getValues(false);

        for (String key : keyValues.keySet()) {
            if (key.equals("events"))
                continue;

            Object value = keyValues.get(key);
            String val = value.toString();

            if (value instanceof ConfigurationSection) {
                ConfigurationSection locSection = (ConfigurationSection) value;

                val = "(" + String.format("%.2f", locSection.getDouble("x")) + ", "
                        + String.format("%.2f", locSection.getDouble("y")) + ", "
                        + String.format("%.2f", locSection.getDouble("z")) + ") ["
                        + String.format("%.2f", locSection.getDouble("yaw")) + ", "
                        + String.format("%.2f", locSection.getDouble("pitch")) + "]";
            }

            message.add(ChatColor.GOLD + key + ": " + ChatColor.YELLOW + val);
        }

        return message.toArray(new String[0]);
    }
}
