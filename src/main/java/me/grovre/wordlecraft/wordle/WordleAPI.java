package me.grovre.wordlecraft.wordle;

import com.google.common.io.Files;
import me.grovre.wordlecraft.Keys;
import me.grovre.wordlecraft.WordleCraft;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.K;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class WordleAPI {

    // Will mostly be used for '/wordle share'

    public static ArrayList<WordleGameInstance> wordleGameInstances = new ArrayList<>();
    public static HashMap<UUID, WordleGameInstance> previousGameInstances = new HashMap<>();

    public static void transferInstanceToPreviousInstances(WordleGameInstance gameInstance) {
        if(wordleGameInstances.remove(gameInstance)) {
            previousGameInstances.put(gameInstance.getPlayer().getUniqueId(), gameInstance);
        }
    }

    public static ArrayList<String> getAllWords() {
        // File ops
        File f = new File(WordleCraft.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "words.txt");
        if(!f.getParentFile().exists()) if(!f.mkdir()) System.out.println("Failed to create directory: "
                + f.getParentFile().getAbsolutePath());
        if(!f.exists()) WordleCraft.createWordFile();

        // Reads file and saves all lines/words to ArrayList<String>
        try {
            return (ArrayList<String>) Files.readLines(f, Charset.defaultCharset()); // Thanks, Google
        } catch (IOException e) {
            System.out.println("Failed to read lines from file: " + f.getAbsolutePath());
            e.printStackTrace();
        }
        return null;
    }

    public static String getRandomWord() {
        // Gets all words and returns a random one
        ArrayList<String> allWords = getAllWords();
        assert allWords != null;
        return allWords.get(new Random().nextInt(allWords.size()-1));
    }

    public static void startWordleGame(Player player) {
        new WordleGameInstance(player);
    }

    public static String getSessionWord() {
        return WordleCraft.sessionWord;
    }

    public static void setRandomSessionWord() {
        WordleCraft.sessionWord = getRandomWord();
        WordleCraft.sessionWord = WordleCraft.sessionWord.toUpperCase();
    }

    public static boolean setRandomSessionWord(String word) {
        if(word.length() == 5) {
            WordleCraft.sessionWord = word;
            return true;
        } else {
            System.out.println("Given word '" + word + "' does not have a length of 5.");
            return false;
        }
    }

    public static WordleGameInstance getPlayerGameInstance(Player player) {
        try {
            return wordleGameInstances.get(getIndexOfGameInstance(player));
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static ArrayList<WordleGameInstance> getWordleGameInstances() {
        return wordleGameInstances;
    }

    public static void removeFromGameInstances(Player player) {
        try {
            Objects.requireNonNull(getPlayerGameInstance(player)).setPlayerInstance(false);
        } catch (NullPointerException ignored) {}
    }

    public static Integer getIndexOfGameInstance(Player player) {
        return player.getPersistentDataContainer().get(Keys.hasInstanceKey, PersistentDataType.INTEGER);
    }

    public static Integer getIndexOfGameInstance(WordleGameInstance gameInstance) {
        return gameInstance.getPlayer().getPersistentDataContainer().get(Keys.hasInstanceKey, PersistentDataType.INTEGER);
    }

    public static boolean gameInstancesContains(Player player) {
        return getWordleGameInstances().contains(getPlayerGameInstance(player));
    }

    public static boolean gameInstancesContains(WordleGameInstance gameInstance) {
        return getWordleGameInstances().contains(gameInstance);
    }

    public static boolean serverHasAnyActiveGameInstances() {
        return wordleGameInstances.size() > 0;
    }

    public static int getWinCount(Player player) {
        Integer wins = player.getPersistentDataContainer().get(Keys.winCountKey, PersistentDataType.INTEGER);
        return wins == null ? 0 : wins;
    }

    public static int getLossCount(Player player) {
        Integer lost = player.getPersistentDataContainer().get(Keys.lostCountKey, PersistentDataType.INTEGER);
        return lost == null ? 0 : lost;
    }

    public static int getTotalGames(Player player) {
        return getWinCount(player) + getLossCount(player);
    }

    public static double getWinRatePercentage(Player player) {
        return ((double) getWinCount(player) / getTotalGames(player)) * 100;
    }

    public static void addToWinCount(Player player, int n) {
        int currentWins = getWinCount(player);
        player.getPersistentDataContainer().set(Keys.winCountKey, PersistentDataType.INTEGER, currentWins + n);
    }

    public static void addToLossCount(Player player, int n) {
        int currentLosses = getLossCount(player);
        player.getPersistentDataContainer().set(Keys.lostCountKey, PersistentDataType.INTEGER, currentLosses + n);
    }

    public static int getGuessCount(Player player) {
        Integer guessCount = player.getPersistentDataContainer().get(Keys.guessCountKey, PersistentDataType.INTEGER);
        return guessCount == null ? 0 : guessCount;
    }

    public static void addToGuessCount(Player player, int n) {
        int currentGuesses = getGuessCount(player);
        player.getPersistentDataContainer().set(Keys.guessCountKey, PersistentDataType.INTEGER, currentGuesses + n);
    }

    public static boolean hasCompletedSessionWord(Player player) {
        Integer status = player.getPersistentDataContainer().get(Keys.sessionCompletedKey, PersistentDataType.INTEGER);
        if(status == null) return false;
        return status == 1;
    }

    public static void setSessionWordCompleted(Player player, boolean hasCompleted) {
        player.getPersistentDataContainer().set(Keys.sessionCompletedKey, PersistentDataType.INTEGER, hasCompleted ? 1 : 0);
    }
}
