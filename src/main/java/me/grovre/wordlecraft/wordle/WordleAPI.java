package me.grovre.wordlecraft.wordle;

import com.google.common.io.Files;
import me.grovre.wordlecraft.Keys;
import me.grovre.wordlecraft.WordleCraft;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class WordleAPI {

    public static ArrayList<WordleGameInstance> wordleGameInstances = new ArrayList<>();

    public static ArrayList<String> getAllWords() {
        // File ops
        File f = new File(WordleCraft.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "words.txt");
        if(!f.getParentFile().exists()) if(!f.mkdir()) System.out.println("Failed to create directory: " + f.getParentFile().getAbsolutePath());
        if(!f.exists()) WordleCraft.getPlugin().saveResource("src/main/resources/words.txt", false);

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
        // TODO Make random word fetching not use getAllWords() to make it faster
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

    public static void setSessionWord() {
        WordleCraft.sessionWord = getRandomWord();
    }

    public static boolean setSessionWord(String word) {
        if(word.length() == 5) {
            WordleCraft.sessionWord = word;
            return true;
        } else {
            System.out.println("Given word '" + word + "' does not have a length of 5.");
            return false;
        }
    }

    public static WordleGameInstance getPlayerGameInstance(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if(!pdc.has(Keys.hasInstanceKey, PersistentDataType.INTEGER)) {
            System.out.println("The player has never played Wordle before!");
            return null;
        }
        Integer gameIndex = pdc.get(Keys.hasInstanceKey, PersistentDataType.INTEGER);
        if(gameIndex == null) return null;
        return wordleGameInstances.get(gameIndex);
    }

    public static ArrayList<WordleGameInstance> getWordleGameInstances() {
        return wordleGameInstances;
    }

    public static boolean hasActiveGameInstance(Player player) {
        return wordleGameInstances.size() > 0;
    }
}
