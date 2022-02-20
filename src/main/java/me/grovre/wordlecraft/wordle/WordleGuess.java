package me.grovre.wordlecraft.wordle;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class WordleGuess {

    public WordleGuess(Player player, WordleGameInstance gameInstance, String guess) {
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

        player.sendMessage(formattedGuess.toString() + ChatColor.AQUA + "\n---------");
        gameInstance.makeGuess(guess);
    }

    public void promptGuess(Player player, WordleGameInstance gameInstance) {
        int guessNumber = gameInstance.getGuesses().size();
        player.sendMessage(ChatColor.AQUA + "Enter guess #" + guessNumber + ": ");
    }
}
