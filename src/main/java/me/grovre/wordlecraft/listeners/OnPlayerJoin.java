package me.grovre.wordlecraft.listeners;

import me.grovre.wordlecraft.wordle.WordleAPI;
import me.grovre.wordlecraft.wordle.WordleGameInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void OnPlayerJoinServer(PlayerJoinEvent event) {

        // Used to double make sure that the server didn't fail to check for any game instances
        Player player = event.getPlayer();
        WordleGameInstance gameInstance = WordleAPI.getPlayerGameInstance(player);
        WordleAPI.removeFromGameInstances(player);
    }

}
