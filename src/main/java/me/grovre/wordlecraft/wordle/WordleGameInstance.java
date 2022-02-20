package me.grovre.wordlecraft.wordle;

import me.grovre.wordlecraft.Keys;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class WordleGameInstance {

    private final Player player;
    private final String word;
    private ArrayList<String> guesses;

    public WordleGameInstance(Player player) {
        this.player = player;
        // TODO Word will only be session word if not solved yet
        word = WordleAPI.getSessionWord();
        new WordleStart().startGame(player);


        // TODO continue the game in steps with chat interception and classes
    }

    public void setPlayerInstance(Player player, boolean isPlaying) {

        PersistentDataContainer pdc = player.getPersistentDataContainer();
        // Value is 1 if playing, 0 if not
        pdc.set(Keys.hasInstanceKey, PersistentDataType.INTEGER, isPlaying ? 1 : 0);

    }
}
