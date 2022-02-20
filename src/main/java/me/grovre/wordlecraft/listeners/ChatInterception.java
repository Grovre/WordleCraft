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

        WordleGameInstance gameInstance = WordleAPI.getPlayerGameInstance(player);
        if(gameInstance == null) return;

        String guess = event.getMessage();
        if(guess.length() != 5) return;

        event.setCancelled(true);

        gameInstance.makeGuess(guess);

        // TODO Complete chat interception when the player has an active game instance

    }

}
