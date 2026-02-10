package fr.kikoplugins.kikoapi;

import fr.kikoplugins.kikoapi.lang.Lang;
import fr.kikoplugins.kikoapi.updatechecker.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

public class KikoAPI extends JavaPlugin {
    private static final String MODRINTH_PROJECT_ID = "nwOXHH0K";

    private static KikoAPI instance;

    public static Lang LANG;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        LANG = Lang.builder(this)
                .addDefaultLanguageFiles("en_US.yml", "fr_FR.yml")
                .build();

        if (this.getConfig().getBoolean("update-checker.enabled", true))
            new UpdateChecker(this, MODRINTH_PROJECT_ID);
    }

    @Override
    public void onDisable() {

    }

    public static KikoAPI getInstance() {
        return instance;
    }
}
