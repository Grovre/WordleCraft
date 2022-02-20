package me.grovre.wordlecraft.wordle;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WordleGuess {

    public WordleGuess(Player player, WordleGameInstance gameInstance) {
        gameInstance
    }

    public void promptGuess(Player player, WordleGameInstance gameInstance) {
        int guessNumber = gameInstance.getGuesses().size();
        player.sendMessage(ChatColor.AQUA + "Enter guess #" + guessNumber + ": ");
    }
}
