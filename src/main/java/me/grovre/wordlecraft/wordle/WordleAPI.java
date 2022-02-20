package me.grovre.wordlecraft.wordle;

import com.google.common.io.Files;
import me.grovre.wordlecraft.WordleCraft;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class WordleAPI {

    public static ArrayList<String> getAllWords() {
        // File ops
        File f = new File(WordleCraft.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "words.txt");
        if(!f.getParentFile().exists()) if(!f.mkdir()) System.out.println("Failed to create directory: " + f.getParentFile().getAbsolutePath());
        if(!f.exists()) WordleCraft.getPlugin().saveResource("src/main/resources/words.txt", false);

        // Reads file and saves all lines/words to array
        try {
            return (ArrayList<String>) Files.readLines(f, Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Failed to read lines from file: " + f.getAbsolutePath());
            e.printStackTrace();
        }
        return null;
    }

    public static String getRandomWord() {
        ArrayList<String> allWords = getAllWords();
        assert allWords != null;
        return allWords.get(new Random().nextInt(allWords.size()-1));
    }

    public static String getSessionWord() {
        return WordleCraft.sessionWord;
    }

    public static void setSessionWord() {
        WordleCraft.sessionWord = getRandomWord();
    }
}
