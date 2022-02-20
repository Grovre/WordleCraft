package me.grovre.wordlecraft;

import org.bukkit.NamespacedKey;

public class Keys {

    public static NamespacedKey hasInstanceKey;

    public static void loadKeys() {
        hasInstanceKey = new NamespacedKey(WordleCraft.getPlugin(), "hasInstance");
    }
}
