package me.grovre.wordlecraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatInterception implements Listener {

    @EventHandler
    public void OnPlayerSendChatMessage(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        // TODO Complete chat interception when the player has an active game instance

    }

}
