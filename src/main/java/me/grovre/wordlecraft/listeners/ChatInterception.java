package me.grovre.wordlecraft.listeners;

import me.grovre.wordlecraft.wordle.WordleAPI;
import me.grovre.wordlecraft.wordle.WordleGameInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatInterception implements Listener {

    @EventHandler
    public void OnPlayerSendChatMessage(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        System.out.println(player.getName() + " sent a message");

        WordleGameInstance gameInstance = WordleAPI.getPlayerGameInstance(player);
        if(gameInstance == null) {
            System.out.println("No game instance found");
            return;
        }

        String guess = event.getMessage();
        if(guess.length() != 5) {
            System.out.println("Message is not 5 characters long");
            return;
        }
        System.out.println("Made a 5 char guess!");

        event.setCancelled(true);

        gameInstance.makeGuess(guess);
    }
}
