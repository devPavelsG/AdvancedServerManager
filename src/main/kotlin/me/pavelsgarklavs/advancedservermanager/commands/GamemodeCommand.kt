package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.*
import org.bukkit.entity.Player


class GamemodeCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender is Player) {
            val player: Player = sender

            if (args.size == 1) {
                if ((args[0] == "0") || (args[0].equals("survival", ignoreCase = true))) {
                    if (player.hasPermission("advancedservermanager.survival")) {
                        player.gameMode = GameMode.SURVIVAL
                        player.sendMessage(plugin.getConfigMessage("Survival"))
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                } else if ((args[0] == "1") || (args[0].equals("creative", ignoreCase = true))) {
                    if (player.hasPermission("advancedservermanager.creative")) {
                        player.gameMode = GameMode.CREATIVE
                        player.sendMessage(plugin.getConfigMessage("Creative"))
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                } else if ((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true))) {
                    if (player.hasPermission("advancedservermanager.adventure")) {
                        player.gameMode = GameMode.ADVENTURE
                        player.sendMessage(plugin.getConfigMessage("Adventure"))
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                } else if ((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true))) {
                    if (player.hasPermission("advancedservermanager.spectator")) {
                        player.gameMode = GameMode.SPECTATOR
                        player.sendMessage(plugin.getConfigMessage("Spectator"))
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                }
            } else if (args.size == 2) {
                if (((args[0] == "0") || (args[0].equals("survival", ignoreCase = true))) &&
                    (args[1] == plugin.getSingleOnlinePlayer(args[1])?.displayName)
                ) {
                    if (player.hasPermission("advancedservermanager.survival.players")) {
                        plugin.getSingleOnlinePlayer(args[1])?.gameMode = GameMode.SURVIVAL
                        player.sendMessage(plugin.getConfigMessage("Survival"))
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                } else if (((args[0] == "1") || (args[0].equals("creative", ignoreCase = true))) &&
                    (args[1] == plugin.getSingleOnlinePlayer(args[1])?.displayName)
                ) {
                    if (player.hasPermission("advancedservermanager.creative.players")) {
                        plugin.getSingleOnlinePlayer(args[1])?.gameMode = GameMode.CREATIVE
                        player.sendMessage(plugin.getConfigMessage("Creative"))
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                } else if (((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true))) &&
                    (args[1] == plugin.getSingleOnlinePlayer(args[1])?.displayName)
                ) {
                    if (player.hasPermission("advancedservermanager.adventure.players")) {
                        plugin.getSingleOnlinePlayer(args[1])?.gameMode = GameMode.ADVENTURE
                        player.sendMessage(plugin.getConfigMessage("Adventure"))
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                } else if (((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true))) &&
                    (args[1] == plugin.getSingleOnlinePlayer(args[1])?.displayName)
                ) {
                    if (player.hasPermission("advancedservermanager.spectator.players")) {
                        plugin.getSingleOnlinePlayer(args[1])?.gameMode = GameMode.SPECTATOR
                        player.sendMessage(plugin.getConfigMessage("Spectator"))
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                } else if (plugin.checkOfflinePlayer(args[1]) == null) {
                    player.sendMessage(plugin.getConfigMessage("OfflineOrDoesNotExist"))
                }
            }
        }
        if (sender is ConsoleCommandSender) {
            if (args.isEmpty()) {
                println("\u001b[31mPlease provide an argument: \u001B[32m/gm [gamemode/0-3] [player]\u001B[0m")
            } else if (args.size == 1) {
                println("\u001b[31mPlease provide a player to change gamemode: \u001b[32m/gm [gamemode/0-3] [player]\u001b[0m")
            } else if (args.size == 2) {
                if (((args[0] == "0") || (args[0].equals("survival", ignoreCase = true))) &&
                    (args[1] == plugin.getSingleOnlinePlayer(args[1])?.displayName)
                ) {
                    plugin.getSingleOnlinePlayer(args[1])?.gameMode = GameMode.SURVIVAL
                    println("\u001B[32mGamemode changed for ${args[1]} to Survival\u001B[0m")
                } else if (((args[0] == "1") || (args[0].equals("creative", ignoreCase = true))) &&
                    (args[1] == plugin.getSingleOnlinePlayer(args[1])?.displayName)
                ) {
                    plugin.getSingleOnlinePlayer(args[1])?.gameMode = GameMode.CREATIVE
                    println("\u001B[32mGamemode changed for ${args[1]} to Creative\u001B[0m")
                } else if (((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true))) &&
                    (args[1] == plugin.getSingleOnlinePlayer(args[1])?.displayName)
                ) {
                    plugin.getSingleOnlinePlayer(args[1])?.gameMode = GameMode.ADVENTURE
                    println("\u001B[32mGamemode changed for ${args[1]} to Adventure\u001B[0m")
                } else if (((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true))) &&
                    (args[1] == plugin.getSingleOnlinePlayer(args[1])?.displayName)
                ) {
                    plugin.getSingleOnlinePlayer(args[1])?.gameMode = GameMode.SPECTATOR
                    println("\u001B[32mGamemode changed for ${args[1]} to Spectator\u001B[0m")
                } else if (plugin.checkOfflinePlayer(args[1]) == null) {
                    println("\u001b[31mPlayer is offline or does not exist!\u001b[0m")
                }
            }
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        val completions: MutableList<String> = mutableListOf()
        if (args.size == 1) {
            completions.add("0")
            completions.add("1")
            completions.add("2")
            completions.add("3")
            completions.add("survival")
            completions.add("creative")
            completions.add("adventure")
            completions.add("spectator")
        } else if (args.size == 2) {
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.displayName)
            }
        }
        return completions
    }
}