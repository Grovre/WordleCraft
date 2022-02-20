package me.grovre.wordlecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.HashMap;

public class Permissions {

    // TODO make a static hashmap of all the commands, using the permission name to make adding permissions easier, ex: <String permName, Permission>
    public static Permission startWordle;

    public Permissions() {
        loadPermissions();
        System.out.println("Wordle permissions created and loaded");
    }

    public static void loadPermissions() {
        startWordle = new Permission("wordlecraft.startWordle",
                "Allows users to use /wordle start");
    }

    public static void sendNoPermissionsMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You don't have permission to run this command!");
    }

    public static void sendNoPermissionsMessage(Player player, String command) {
        player.sendMessage(ChatColor.RED + "You don't have permission to run " + command);
    }

}
