package me.grovre.wordlecraft.commands;

import me.grovre.wordlecraft.Permissions;
import me.grovre.wordlecraft.wordle.WordleAPI;
import me.grovre.wordlecraft.wordle.WordleGameInstance;
import org.bukkit.ChatColor;
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

        // Will throw indexOutOfBoundsException with everything trying to check the first argument. /wordle is not a command itself
        // so until then, just make it do '/wordle help'
        // TODO Possible command for just 'wordle'?
        if(args.length == 0) {
            args = new String[]{"help"};
        }

        // TODO arg tab completion
        if(args[0].equalsIgnoreCase("start")) {
            if(player == null) {
                System.out.println("Command must be sent from a player!");
                return true;
            } else if(!player.hasPermission(Permissions.startWordle)) {
                Permissions.sendNoPermissionsMessage(player);
                return true;
            } else if(WordleAPI.getPlayerGameInstance(player) != null) {
                player.sendMessage(ChatColor.RED + "You are already have an instance of Wordle running!");
                System.out.println(player.getName() + " is already playing Wordle!");
                return true;
            } else {
                new WordleGameInstance(player);
                return true;
            }
        }

        if(args[0].equalsIgnoreCase("help")) {
            if(player != null) {
                player.sendMessage(ChatColor.AQUA + "With Wordlecraft, you are able to play Wordle within the chat! " +
                        "Just type '/wordle start' and you'll begin your game!\n" +
                        "Once you complete the session word that resets everytime the server restarts, " +
                        "you'll begin to receive random words. Any messages that are 5 characters long " +
                        "are intercepted and played into the game.");
            }
            return true;
        }

        if(args[0].equalsIgnoreCase("stop") && player != null) {
            WordleGameInstance gameInstance = WordleAPI.getPlayerGameInstance(player);
            if(gameInstance != null) {
                player.sendMessage(ChatColor.AQUA + "You have chosen to stop playing Wordle! Better luck next time!");
                gameInstance.setPlayerInstance(false);
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
