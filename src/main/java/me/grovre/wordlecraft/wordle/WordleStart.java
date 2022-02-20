package me.grovre.wordlecraft.wordle;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.List;

public class WordleStart {

    public void startGame(Player player, WordleGameInstance gameInstance) {
        System.out.println("startGame");
        if(isPlayerSafe(player) > 0) {
            System.out.println("Player not safe");
            player.sendMessage(ChatColor.RED + "You aren't safe! There are monsters nearby!");
            return;
        }
        System.out.println("Game instance true");
        gameInstance.setPlayerInstance(player, true);
        System.out.println("Index of game instance: " + WordleAPI.getPlayerGameInstance(player));
    }

    public int isPlayerSafe(Player player) {
        // TODO Add radiusToCheck to config and get it from there
        double radiusToCheck = 8;
        List<Entity> nearbyEntities = player.getNearbyEntities(radiusToCheck, 5, radiusToCheck);
        // Stream counting all Monster instances
        return (int) nearbyEntities.stream().filter(e -> e instanceof Monster).count();
    }
}
