package me.grovre.wordlecraft.wordle;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.List;

public class WordleStart {

    public void startGame(Player player, WordleGameInstance gameInstance) {
        System.out.println("startGame");
        gameInstance.setPlayerInstance(player, true);
        System.out.println("Index of game instance: " + WordleAPI.getIndexOfGameInstance(player));
    }
}
