package me.grovre.wordlecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class Permissions {

    // TODO make a static hashmap of all the commands, using the permission name to make adding permissions easier, ex: <String permName, Permission>
    public static Permission startWordle;
    public static Permission setWordle;

    public static void loadPermissions() {
        startWordle = new Permission("wordlecraft.startWordle",
                "Allows users to use /wordle start");
        setWordle = new Permission("wordlecraft.setWordle",
                "Allows anybody with this permission to change the session word");
    }

    public static void sendNoPermissionsMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You don't have permission to run this command!");
    }

    public static void sendNoPermissionsMessage(Player player, String command) {
        player.sendMessage(ChatColor.RED + "You don't have permission to run " + command);
    }

}
