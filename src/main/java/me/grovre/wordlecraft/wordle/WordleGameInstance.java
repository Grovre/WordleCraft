package me.grovre.wordlecraft.wordle;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WordleGameInstance {

    private Inventory gameInv;
    private final Player player;
    private final String word;

    public WordleGameInstance(Player player) {
        this.player = player;
        // TODO Word will only be session word if not solved yet
        word = WordleAPI.getSessionWord();
        gameInv = new WordleStart().startGame(player);

        // TODO continue the game in steps with listeners and classes
    }
}
