package me.grovre.wordlecraft.listeners;

import me.grovre.wordlecraft.wordle.WordleAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerLeave implements Listener {

    @EventHandler
    public void OnPlayerLeaveServer(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        WordleAPI.removeFromGameInstances(player);
    }

}
