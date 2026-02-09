package fr.kikoplugins.kikoapi;

import fr.kikoplugins.kikoapi.updatechecker.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

public class KikoAPI extends JavaPlugin {
    private static KikoAPI instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (this.getConfig().getBoolean("update-checker.enabled", true))
            new UpdateChecker(this, "nwOXHH0K");
    }

    @Override
    public void onDisable() {

    }

    public static KikoAPI getInstance() {
        return instance;
    }
}
