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
    private final ArrayList<WordleGuess> guesses;
    boolean won = false;

    public WordleGameInstance(Player player) {
        System.out.println("New WordleGameInstance");
        this.player = player;
        guesses = new ArrayList<>(6);
        word = WordleAPI.hasCompletedSessionWord(player) ? WordleAPI.getRandomWord().toUpperCase() : WordleAPI.getSessionWord().toUpperCase();
        won = false;
        System.out.println("startGame");
        setPlayerInstance(true);
        System.out.println(player.getName() + " has the word " + word);
        System.out.println("Index of game instance: " + WordleAPI.getIndexOfGameInstance(player));
        player.sendMessage(ChatColor.AQUA + "You have started a game of " + ChatColor.YELLOW + "Wordle! "
                + ChatColor.AQUA + "Any messages you send that are 5 characters long will be intercepted and at play!\nEnter guess #1: ");
    }

    public void endGameInstance() {
        new WordleEnd().endGame(getPlayer(), this);
        WordleAPI.transferInstanceToPreviousInstances(this);
        player.sendMessage(ChatColor.AQUA + "Would you like to share your results?\nUse '/wordle share'");
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
        WordleGuess guess = new WordleGuess(this, guessMade);
        guesses.add(guess);
        System.out.println("Current guess amount: " + guesses.size());
        boolean isRight = guess.checkWithAnswer(this);
        if(isRight) {
            won = true;
            System.out.println("Player guessed right");
            new WordleEnd().endGame(player, this);
            player.sendMessage(ChatColor.AQUA + "You guessed the word correctly!: " + ChatColor.DARK_GREEN + word);
            WordleAPI.addToWinCount(player, 1);
        } else if(guesses.size() >= 6) {
            won = false;
            System.out.println("Over guess limit");
            new WordleEnd().endGame(player, this);
            player.sendMessage(ChatColor.AQUA + "You have run out of guesses! Your word was " + ChatColor.RED + word);
            WordleAPI.addToLossCount(player, 1);
        } else {
            guess.promptGuess(player, this);
        }
    }

    public void addToGuesses(WordleGuess guess) {
        guesses.add(guess);
    }
}
