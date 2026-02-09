package fr.kikoplugins.kikoapi;

import fr.kikoplugins.kikoapi.updatechecker.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class KikoAPI extends JavaPlugin {
    private static final String MODRINTH_PROJECT_ID = "nwOXHH0K";
    private static final int BSTATS_PLUGIN_ID = 29448;

    private static KikoAPI instance;

    private Metrics bStats;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.bStats = new Metrics(this, BSTATS_PLUGIN_ID);

        if (this.getConfig().getBoolean("update-checker.enabled", true))
            new UpdateChecker(this, MODRINTH_PROJECT_ID);
    }

    @Override
    public void onDisable() {
        this.bStats.shutdown();
    }

    public static KikoAPI getInstance() {
        return instance;
    }
}
