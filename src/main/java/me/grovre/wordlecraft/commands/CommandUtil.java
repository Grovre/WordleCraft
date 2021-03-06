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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        updateLatest(player, sender, command, args);

        if(args.length == 0) {
            args = new String[]{"help"};
        }

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
                gameInstance.endGameInstance(false);
                player.sendMessage(ChatColor.AQUA + "You have chosen to stop playing Wordle! Better luck next time!");
            }
        }

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
            if(args[1].equalsIgnoreCase("random")) {
                WordleAPI.setRandomSessionWord();
                if(player != null) {
                    player.sendMessage(ChatColor.AQUA + "Successfully set the session word to something random.");
                }
                System.out.println("Successfully set the session word to something random.");
            }
            if (args[1].length() != 5) {
                System.out.println(ChatColor.RED + "Your word must be 5 characters long!");
                return false;
            }
            WordleAPI.setRandomSessionWord(args[1]);
            if (player != null) {
                player.sendMessage(ChatColor.AQUA + "Successfully set word to: " + ChatColor.DARK_GREEN + args[1].toUpperCase());
            }
        }

        if(args[0].equalsIgnoreCase("stats")) {
            Player dataPlayer = player;
            if(args.length == 2 && player != null && player.hasPermission(Permissions.wordleStats)) {
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
            } else if(player == null) {
                System.out.println("You must be a player to use '/wordle data'");
                return true;
            }

            assert dataPlayer != null;
            int wins = WordleAPI.getWinCount(dataPlayer);
            int losses = WordleAPI.getLossCount(dataPlayer);
            int totalGames = WordleAPI.getTotalGames(dataPlayer);
            int guesses = WordleAPI.getGuessCount(dataPlayer);
            double winRate = WordleAPI.getWinRatePercentage(dataPlayer);
            winRate = ((int) (winRate*100))/100D;
            double averageGuessesPerMatch = (double) guesses / totalGames;
            averageGuessesPerMatch = ((int) (averageGuessesPerMatch*100))/100D;
            player.sendMessage(ChatColor.AQUA + dataPlayer.getName() + " has: ");
            player.sendMessage(ChatColor.AQUA + "Wins: " + ChatColor.DARK_GREEN + wins);
            player.sendMessage(ChatColor.AQUA + "Losses: " + ChatColor.RED + losses);
            player.sendMessage(ChatColor.AQUA + "Total games: " + ChatColor.DARK_AQUA + totalGames);
            player.sendMessage(ChatColor.AQUA + "Total guesses: " + ChatColor.DARK_AQUA + guesses);
            player.sendMessage(ChatColor.AQUA + "Average guesses per game: " + ChatColor.DARK_AQUA + averageGuessesPerMatch);
            ChatColor rateColor = winRate >= 50 ? ChatColor.DARK_GREEN : ChatColor.RED;
            player.sendMessage(ChatColor.AQUA + "Win rate: " + rateColor + winRate + "%");
            return true;
        }

        if(args[0].equalsIgnoreCase("share") && player != null) {
            WordleGameInstance gi = WordleAPI.previousGameInstances.get(player.getUniqueId());
            if(gi == null) {
                player.sendMessage(ChatColor.DARK_AQUA + "You need to play a game of Wordle first! /wordle start");
                return true;
            }
            player.sendMessage(ChatColor.AQUA + "Sharing your most recent game!");
            String word = gi.getWord().equalsIgnoreCase(WordleAPI.getSessionWord()) ? ChatColor.RED + "the session word!" : ChatColor.DARK_AQUA + gi.getWord().toUpperCase() + ChatColor.AQUA;
            String s = ChatColor.AQUA + player.getName() + " wants to share their most recent Wordle game!\n"
                    + "The word was " + word + "\n"
                    + ChatColor.AQUA + "They " + (gi.hasWon() ? ChatColor.DARK_GREEN : ChatColor.RED) + ChatColor.BOLD + (gi.hasWon() ? "WON" : "LOST") + "!\n" + ChatColor.RESET
                    + ChatColor.AQUA + (gi.hasWon() ? "It took " + ChatColor.DARK_AQUA + ChatColor.BOLD + gi.getGuesses().size() + "/6" + ChatColor.RESET + ChatColor.AQUA + " guesses!\n" : "")
                    + ChatColor.AQUA + player.getName() + " now has " + ChatColor.DARK_AQUA + ChatColor.BOLD + WordleAPI.getTotalGames(player) + ChatColor.RESET + ChatColor.AQUA + " total games of Wordle!\n"
                    + ChatColor.DARK_AQUA + "See more with '/wordle stats " + player.getName() + "'";
            for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage(s);
            WordleAPI.previousGameInstances.remove(player.getUniqueId());
        }

        return true;
    }

    public void updateLatest(Player player, CommandSender commandSender, Command command, String[] args) {
        lastRunCommand = command;
        lastWordlePlayerCommander = player;
        lastWordleCommandSender = commandSender;
        lastArgsUsed = args;
    }
}
