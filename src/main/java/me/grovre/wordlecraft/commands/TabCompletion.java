package me.grovre.wordlecraft.commands;

import me.grovre.wordlecraft.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {

    /*
    All possible current commands:
    /wordle start
    /wordle help
    /wordle stop
    /wordle set <Word>
    /wordle set random
    /wordle stats
    /wordle stats <Player>
    /wordle share
     */

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        List<String> tab = new ArrayList<>();
        if(args.length == 1)
        tab.add("start");
        tab.add("stop");
        tab.add("help");
        tab.add("stats");
        tab.add("share");
        if(player != null && player.hasPermission(Permissions.setWordle)) {
            tab.add("set");
        }
        if (args.length == 2) {
            tab.clear();
            if (args[0].equalsIgnoreCase("set")) {
                tab.add("random");
                tab.add("<Word>");
            }
        }

        return tab;
    }
}
