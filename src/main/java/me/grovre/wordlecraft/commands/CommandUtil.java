package me.grovre.wordlecraft.commands;

import me.grovre.wordlecraft.Permissions;
import me.grovre.wordlecraft.wordle.WordleGameInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtil implements CommandExecutor {

    // Here for unknown reasons but will stay
    public static Command lastRunCommand;
    public static CommandSender lastWordleCommandSender;
    public static Player lastWordlePlayerCommander;
    public static String[] lastArgsUsed;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        updateLatest(player, sender, command, args);

        if(args[0].equals("start")) {
            if(player == null) {
                System.out.println("Command must be sent from a player!");
            } else if(!player.hasPermission(Permissions.startWordle)) {
                Permissions.sendNoPermissionsMessage(player);
            } else {
                new WordleGameInstance(player);
            }
        }

        // TODO Add more commands

        return true;
    }

    public void updateLatest(Player player, CommandSender commandSender, Command command, String[] args) {
        lastRunCommand = command;
        lastWordlePlayerCommander = player;
        lastWordleCommandSender = commandSender;
        lastArgsUsed = args;
    }
}
