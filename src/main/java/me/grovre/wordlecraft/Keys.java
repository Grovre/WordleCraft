package me.grovre.wordlecraft;

import org.bukkit.NamespacedKey;

public class Keys {

    public static NamespacedKey hasInstanceKey;
    public static NamespacedKey lostCountKey;
    public static NamespacedKey winCountKey;

    public static void loadKeys() {
        WordleCraft p = WordleCraft.getPlugin();
        hasInstanceKey = new NamespacedKey(p, "hasInstance");
        lostCountKey = new NamespacedKey(p, "lostCount");
        winCountKey = new NamespacedKey(p, "winCount");
    }
}
