package me.grovre.wordlecraft;

import me.grovre.wordlecraft.commands.CommandUtil;
import me.grovre.wordlecraft.wordle.WordleAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class WordleCraft extends JavaPlugin {

    public static WordleCraft plugin;
    public static String sessionWord;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Permissions.loadPermissions();
        sessionWord = WordleAPI.getRandomWord();

        Objects.requireNonNull(getServer().getPluginCommand("wordle")).setExecutor(new CommandUtil());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static WordleCraft getPlugin() {
        return plugin;
    }
}
