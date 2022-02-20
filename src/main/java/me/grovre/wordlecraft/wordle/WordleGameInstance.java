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
        guesses = new ArrayList<>(5);
        // TODO Word will only be session word if not solved yet
        word = WordleAPI.getSessionWord();
        WordleAPI.wordleGameInstances.add(this);
        new WordleStart().startGame(player, this);

        // TODO continue the game in steps with chat interception and classes

        new WordleEnd().endGame(player, this);
    }

    public void setPlayerInstance(Player player, boolean isPlaying) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();

        // If false, removes this from WordleAPI's static list of game instances
        if(!isPlaying) {
            System.out.println("Is no longer playing Wordle!");
            pdc.set(Keys.hasInstanceKey, PersistentDataType.INTEGER, -1);
            WordleAPI.wordleGameInstances.remove(this);
        }

        // If the WordleAPI static list of game instances doesn't contain this already, add this
        if(!WordleAPI.wordleGameInstances.contains(this)) WordleAPI.wordleGameInstances.add(this);
        // Value is >0 if playing, 0 if not
        int gameIndex = WordleAPI.wordleGameInstances.indexOf(this);
        pdc.set(Keys.hasInstanceKey, PersistentDataType.INTEGER, gameIndex);
    }

    public Player getPlayer() {
        return player;
    }

    public String getWord() {
        return word;
    }

    public ArrayList<String> getGuesses() {
        return guesses;
    }

    public void makeGuess(String guess) {
        guess = guess.toLowerCase();
        guesses.add(guess);
        if(guess.equals(word)) {
            // TODO Make a congratulations for guessing right
            new WordleEnd();
        }
    }
}
