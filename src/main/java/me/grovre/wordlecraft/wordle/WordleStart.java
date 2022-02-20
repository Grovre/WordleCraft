package me.grovre.wordlecraft.wordle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class WordleStart {

    public Inventory startGame(Player player) {

        if(isPlayerSafe(player) > 0) {
            player.sendMessage(ChatColor.RED + "You aren't safe! There are monsters nearby!");
        }
        return createInventory();
    }

    public Inventory createInventory() {
        Inventory gameInv = Bukkit.createInventory(null, InventoryType.CHEST);
        // TODO Fill the inventory with game-related things
        // Specifically letters and the game

        return gameInv;
    }

    public int isPlayerSafe(Player player) {
        // TODO Add radiusToCheck to config and get it from there
        double radiusToCheck = 8;
        List<Entity> nearbyEntities = player.getNearbyEntities(radiusToCheck, 5, radiusToCheck);
        // Stream counting all Monster instances
        return (int) nearbyEntities.stream().filter(e -> e instanceof Monster).count();
    }
}
