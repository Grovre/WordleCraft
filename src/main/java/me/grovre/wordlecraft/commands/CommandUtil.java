package me.grovre.wordlecraft.commands;

import me.grovre.wordlecraft.Permissions;
import me.grovre.wordlecraft.wordle.WordleAPI;
import me.grovre.wordlecraft.wordle.WordleGameInstance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

        // TODO make it so "random" can be used as an arg to set wordle to new RANDOM word instead of what you pass as param
        if(args[0].equalsIgnoreCase("set")) {
            if (player != null) {
                if (!player.hasPermission(Permissions.setWordle)) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to set the session word.");
                }
            }
            if (args.length < 2) {
                if (player != null) {
                    player.sendMessage(ChatColor.RED + "You need a second argument, the word!");
                }
                System.out.println("Need a second argument.");
                return true;
            }
            if (args[1].length() != 5) {
                System.out.println(ChatColor.RED + "Your word must be 5 characters long!");
                return false;
            }
            WordleAPI.setSessionWord(args[1]);
            if (player != null) {
                player.sendMessage(ChatColor.AQUA + "Successfully set word to: " + ChatColor.DARK_GREEN + args[1].toUpperCase());
            }
        }

        if(args[0].equalsIgnoreCase("data")) {
            Player dataPlayer = player;
            if(args.length == 2 && player != null) {
                ArrayList<String> onlinePlayerNames = (ArrayList<String>) Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());
                String argumentName = args[1].toLowerCase();
                if(onlinePlayerNames.contains(argumentName)) {
                    dataPlayer = Bukkit.getPlayer(argumentName);
                } else {
                    player.sendMessage(ChatColor.RED + "Player " + args[1] + " is not online right now.");
                    return true;
                }
            } else if(player != null) {
                System.out.println("You must be a player to use '/wordle data' without any player name passed as your 2nd argument!");
                return true;
            }

            assert dataPlayer != null;
            int wins = WordleAPI.getWinCount(dataPlayer);
            int losses = WordleAPI.getLossCount(dataPlayer);
            int total = WordleAPI.getTotalGames(dataPlayer);
            double winRate = WordleAPI.getWinRatePercentage(dataPlayer);
            player.sendMessage(ChatColor.AQUA + "You have: ");
            player.sendMessage(ChatColor.AQUA + "Wins: " + ChatColor.DARK_GREEN + wins);
            player.sendMessage(ChatColor.AQUA + "Losses: " + ChatColor.RED + losses);
            player.sendMessage(ChatColor.AQUA + "Total games: " + ChatColor.DARK_AQUA + total);
            ChatColor winRateColor = winRate >= 50 ? ChatColor.DARK_GREEN : ChatColor.RED;
            player.sendMessage(ChatColor.AQUA + "Win rate: " + winRateColor + "%" + winRate);
            return true;
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
