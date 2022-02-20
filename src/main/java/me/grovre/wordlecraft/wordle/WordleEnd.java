package me.grovre.wordlecraft.wordle;

import org.bukkit.entity.Player;

public class WordleEnd {

    public void endGame(Player player, WordleGameInstance gameInstance) {
        gameInstance.setPlayerInstance(player, false);
    }

}
