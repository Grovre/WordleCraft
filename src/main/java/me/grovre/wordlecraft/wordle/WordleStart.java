package me.grovre.wordlecraft.wordle;

import de.themoep.inventorygui.GuiElement;
import de.themoep.inventorygui.InventoryGui;
import me.grovre.wordlecraft.WordleCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WordleStart {

    public void startGame(Player player) {

        if(isPlayerSafe(player) > 0) {
            player.sendMessage(ChatColor.RED + "You aren't safe! There are monsters nearby!");
        }
    }

    /*
    Incomplete Method for gui elements
    public Inventory createInventory(Player player) {
        // https://docs.phoenix616.dev/inventorygui/de/themoep/inventorygui/package-summary.html

        String[] guiSetup = {
                "  abcde  ",
                "  fghij  ",
                "  klmno  ",
                "  pqrst  ",
                "  uvwxy  ",
                "         "
        };

        InventoryGui inv = new InventoryGui(WordleCraft.getPlugin(), player, "Wordle!", guiSetup);
        inv.setFiller(new ItemStack(Material.CYAN_WOOL, 1));

        // Incomplete

        return inv;
    }
     */

    public int isPlayerSafe(Player player) {
        // TODO Add radiusToCheck to config and get it from there
        double radiusToCheck = 8;
        List<Entity> nearbyEntities = player.getNearbyEntities(radiusToCheck, 5, radiusToCheck);
        // Stream counting all Monster instances
        return (int) nearbyEntities.stream().filter(e -> e instanceof Monster).count();
    }
}
