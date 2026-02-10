package fr.kikoplugins.kikoapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class KikoAPI extends JavaPlugin {
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static boolean isUnitTest() {
        return Bukkit.getVersion().contains("MockBukkit");
    }
}
