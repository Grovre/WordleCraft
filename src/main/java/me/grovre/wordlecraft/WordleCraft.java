package me.grovre.wordlecraft;

import me.grovre.wordlecraft.commands.CommandUtil;
import me.grovre.wordlecraft.listeners.ChatInterception;
import me.grovre.wordlecraft.listeners.OnPlayerLeave;
import me.grovre.wordlecraft.wordle.WordleAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class WordleCraft extends JavaPlugin {

    public static WordleCraft plugin;
    public static String sessionWord;

    /*
    PDC key: hasInstance
     */

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Permissions.loadPermissions();
        Keys.loadKeys();
        createWordFile();
        sessionWord = WordleAPI.getRandomWord();
        WordleAPI.wordleGameInstances.clear();

        Objects.requireNonNull(getServer().getPluginCommand("wordle")).setExecutor(new CommandUtil());

        getServer().getPluginManager().registerEvents(new ChatInterception(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Removes all online players from any active game instances
        Bukkit.getOnlinePlayers().forEach(WordleAPI::removeFromGameInstances);
    }

    public static WordleCraft getPlugin() {
        return plugin;
    }

    public static void createWordFile() {
        plugin.saveResource("words.txt", true);
    }
}
