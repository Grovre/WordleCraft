package me.grovre.wordlecraft.wordle;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WordleStart {

    public void startGame(Player player, WordleGameInstance gameInstance) {
        System.out.println("startGame");
        gameInstance.setPlayerInstance(true);
        System.out.println("Index of game instance: " + WordleAPI.getIndexOfGameInstance(player));
        player.sendMessage(ChatColor.AQUA + "You have started a game of " + ChatColor.YELLOW + "Wordle! "
                + ChatColor.AQUA + "Any messages you send that are 5 characters long will be intercepted and at play!");
    }
}
