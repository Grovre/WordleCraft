package me.grovre.wordlecraft.wordle;

import org.bukkit.entity.Player;

public class WordleEnd {

    public void endGame(Player player, WordleGameInstance gameInstance) {
        System.out.println("endGame");
        gameInstance.setPlayerInstance(player, false);

        // TODO Add more to what happens at the end of a game
    }

}
