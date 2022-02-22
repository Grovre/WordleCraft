package me.grovre.wordlecraft.wordle;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WordleEnd {

    public void endGame(Player player, WordleGameInstance gameInstance) {
        System.out.println("endGame");
        gameInstance.setPlayerInstance(false);
        player.sendMessage(ChatColor.DARK_AQUA + "Your game has ended!");
    }

    public void endGame(Player player, WordleGameInstance gameInstance, boolean hasWon) {
        endGame(player, gameInstance);
        if(hasWon) {
            WordleAPI.addToWinCount(player, 1);
        } else {
            WordleAPI.addToLossCount(player, 1);
        }
    }
}
