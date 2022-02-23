package me.grovre.wordlecraft;

import me.grovre.wordlecraft.commands.CommandUtil;
import me.grovre.wordlecraft.commands.TabCompletion;
import me.grovre.wordlecraft.listeners.ChatInterception;
import me.grovre.wordlecraft.listeners.OnPlayerLeave;
import me.grovre.wordlecraft.wordle.WordleAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class WordleCraft extends JavaPlugin {

    // TODO Comment on everything!!!
    // TODO Try to remove all warnings!!!

    public static WordleCraft plugin;
    public static String sessionWord;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Permissions.loadPermissions();
        Keys.loadKeys();
        {
            long start = System.currentTimeMillis();
            createWordFile();
            long end = System.currentTimeMillis();
            System.out.println("It took " + (end - start)/1000.0 + " seconds (" + (end-start) + "ms) to create words.txt");
        }
        sessionWord = WordleAPI.getRandomWord();
        WordleAPI.wordleGameInstances.clear();

        Objects.requireNonNull(getServer().getPluginCommand("wordle")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getServer().getPluginCommand("wordle")).setTabCompleter(new TabCompletion());

        getServer().getPluginManager().registerEvents(new ChatInterception(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getOnlinePlayers().forEach(p -> WordleAPI.setSessionWordCompleted(p, false));
        WordleAPI.wordleGameInstances.clear();
        WordleAPI.previousGameInstances.clear();
    }

    public static WordleCraft getPlugin() {
        return plugin;
    }

    public static void createWordFile() {
        plugin.saveResource("words.txt", true);
    }
}
