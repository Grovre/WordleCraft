package me.grovre.wordlecraft.wordle;

import me.grovre.wordlecraft.Keys;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class WordleGameInstance {

    private final Player player;
    private final String word;
    private ArrayList<WordleGuess> guesses;

    public WordleGameInstance(Player player) {
        System.out.println("New WordleGameInstance");
        this.player = player;
        guesses = new ArrayList<>(5);
        // TODO Word will only be session word if not solved yet
        word = WordleAPI.getSessionWord().toUpperCase();
        System.out.println(player.getName() + " has the word " + word);
        new WordleStart().startGame(player, this);
    }

    public void endGameInstance() {
        new WordleEnd().endGame(getPlayer(), this);
    }

    public void setPlayerInstance(boolean isPlaying) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        // If false, removes this from WordleAPI's static list of game instances
        if(!isPlaying) {
            System.out.println("No longer playing Wordle!");
            pdc.set(Keys.hasInstanceKey, PersistentDataType.INTEGER, -1);
            WordleAPI.wordleGameInstances.remove(this);
            return;
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

    public ArrayList<WordleGuess> getGuesses() {
        return guesses;
    }

    public void makeGuess(String guessMade) {
        System.out.println("Player making guess");
        WordleGuess guess = new WordleGuess(getPlayer(), this, guessMade);
        guesses.add(guess);
        System.out.println("Current guess amount: " + guesses.size());
        boolean isRight = guess.checkWithAnswer(this);
        if(isRight) {
            System.out.println("Player guessed right");
            new WordleEnd().endGame(player, this);
            player.sendMessage(ChatColor.AQUA + "You guessed the word correctly!: " + ChatColor.DARK_GREEN + word);
        } else if(guesses.size() >= 6) {
            System.out.println("Over guess limit");
            new WordleEnd().endGame(player, this);
            player.sendMessage(ChatColor.AQUA + "You have run out of guesses! Your word was " + ChatColor.RED + word);
        } else {
            guess.promptGuess(player, this);
        }
    }

    public void addToGuesses(WordleGuess guess) {
        guesses.add(guess);
    }
}
