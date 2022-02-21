package me.grovre.wordlecraft;

import org.bukkit.NamespacedKey;

import javax.naming.Name;

public class Keys {

    public static NamespacedKey hasInstanceKey;
    public static NamespacedKey lostCountKey;
    public static NamespacedKey winCountKey;
    public static NamespacedKey guessCountKey;

    public static void loadKeys() {
        WordleCraft p = WordleCraft.getPlugin();
        hasInstanceKey = new NamespacedKey(p, "hasInstance");
        lostCountKey = new NamespacedKey(p, "lostCount");
        winCountKey = new NamespacedKey(p, "winCount");
        guessCountKey = new NamespacedKey(p, "guessCount");
    }
}
