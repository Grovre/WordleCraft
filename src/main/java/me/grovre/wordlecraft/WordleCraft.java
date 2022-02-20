package me.grovre.wordlecraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class WordleCraft extends JavaPlugin {

    public static WordleCraft plugin;
    public static String sessionWord;

    public static WordleCraft getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
