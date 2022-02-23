package me.grovre.wordlecraft.wordle;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WordleGuess {

    private final String formattedGuess;
    private final String rawGuess;

    public WordleGuess(WordleGameInstance gameInstance, String guess) {
        Player player = gameInstance.getPlayer();
        WordleAPI.addToGuessCount(player, 1);
        guess = guess.toUpperCase();
        this.rawGuess = guess;
        ArrayList<Character> wordChars = new ArrayList<>(5);
        ArrayList<Character> guessChars = new ArrayList<>(5);
        for(char c : gameInstance.getWord().toCharArray()) wordChars.add(c);
        for(char c : guess.toCharArray()) guessChars.add(c);

        StringBuilder formattedGuess = new StringBuilder(5);
        for(int i = 0; i < wordChars.size(); i++) {
            if(guessChars.get(i) == wordChars.get(i)) {
                formattedGuess.append(ChatColor.DARK_GREEN).append(guessChars.get(i));
            } else if(wordChars.contains(guessChars.get(i))) {
                formattedGuess.append(ChatColor.GREEN).append(guessChars.get(i));
            } else {
                formattedGuess.append(ChatColor.GRAY).append(guessChars.get(i));
            }
        }
        this.formattedGuess = formattedGuess.toString();

        player.sendMessage(this.formattedGuess + ChatColor.AQUA + "\n-----");
    }

    public void promptGuess(Player player, WordleGameInstance gameInstance) {
        int guessNumber = gameInstance.getGuesses().size();
        player.sendMessage(ChatColor.AQUA + "Enter guess #" + (guessNumber + 1) + ": ");
    }

    public String getFormattedGuess() {
        return formattedGuess;
    }

    public String getRawGuess() {
        return rawGuess;
    }

    public boolean checkWithAnswer(WordleGameInstance gameInstance) {
        return rawGuess.equalsIgnoreCase(gameInstance.getWord());
    }
}
