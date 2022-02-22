package me.grovre.wordlecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class Permissions {

    public static Permission startWordle;
    public static Permission setWordle;
    public static Permission wordleStats;

    public static void loadPermissions() {
        startWordle = new Permission("wordlecraft.startWordle",
                "Allows users to use /wordle start");
        setWordle = new Permission("wordlecraft.setWordle",
                "Allows anybody with this permission to change the session word");
        wordleStats = new Permission("wordlecraft.stats",
                "Allows you to view the stats of other people's Wordle games");
    }

    public static void sendNoPermissionsMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You don't have permission to run this command!");
    }

    public static void sendNoPermissionsMessage(Player player, String command) {
        player.sendMessage(ChatColor.RED + "You don't have permission to run " + command);
    }

}
