package me.grovre.wordlecraft.wordle;

import me.grovre.wordlecraft.Keys;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class WordleGameInstance {

    private Player player;
    private String word;
    private ArrayList<WordleGuess> guesses;
    boolean won = false;

    public WordleGameInstance(Player player) {
        if(WordleAPI.wordleGameInstances.contains(this)) {
            player.sendMessage(ChatColor.AQUA + "Finish your current Wordle game! Enter any word that is 5 chars long to make your next guess.");
            return;
        }
        // If the WordleAPI static list of game instances doesn't contain this already, add this
        WordleAPI.wordleGameInstances.add(this);
        System.out.println("New WordleGameInstance");
        this.player = player;
        guesses = new ArrayList<>(6);
        word = WordleAPI.hasCompletedSessionWord(player) ? WordleAPI.getRandomWord().toUpperCase() : WordleAPI.getSessionWord().toUpperCase();
        won = false;
        System.out.println("startGame");
        // Value is >-1 if playing, -1 if not
        int gameIndex = WordleAPI.wordleGameInstances.indexOf(this);
        player.getPersistentDataContainer().set(Keys.hasInstanceKey, PersistentDataType.INTEGER, gameIndex);
        System.out.println(player.getName() + " has the word " + word);
        System.out.println("Index of game instance: " + WordleAPI.getIndexOfGameInstance(player));
        player.sendMessage(ChatColor.AQUA + "You have started a game of " + ChatColor.YELLOW + "Wordle! "
                + ChatColor.AQUA + "Any messages you send that are 5 characters long will be intercepted and at play!\nEnter guess #1: ");
    }

    public void endGameInstance(boolean hasWon) {
        System.out.println("endGame");
        System.out.println("No longer playing Wordle!");
        WordleAPI.transferInstanceToPreviousInstances(this);
        player.getPersistentDataContainer().set(Keys.hasInstanceKey, PersistentDataType.INTEGER, -1);
        player.sendMessage(ChatColor.DARK_AQUA + "Your game has ended!");
        player.sendMessage(ChatColor.DARK_AQUA + "Would you like to share your results?\nUse '/wordle share'");
        WordleAPI.setSessionWordCompleted(player, true);
        if(hasWon) {
            WordleAPI.addToWinCount(player, 1);
        } else {
            WordleAPI.addToLossCount(player, 1);
        }
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
            this.endGameInstance(true);
            player.sendMessage(ChatColor.AQUA + "You guessed the word correctly!: " + ChatColor.DARK_GREEN + word);
            WordleAPI.addToWinCount(player, 1);
        } else if(guesses.size() >= 6) {
            won = false;
            System.out.println("Over guess limit");
            this.endGameInstance(false);
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
