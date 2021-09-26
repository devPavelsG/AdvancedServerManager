package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.OfflinePlayer
import org.bukkit.command.*
import org.bukkit.entity.Player


class GamemodeCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {

    private fun getMessage(path: String): String {
        val prefix = plugin.config.getString("Prefix")
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        val message = plugin.config.getString(path)
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        return prefix + message
    }

    private fun getOnlinePlayer(): Player? {
        var singlePlayer: Player? = null
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            singlePlayer = onlinePlayer.player
        }
        return singlePlayer
    }

    private fun getOfflinePlayer(name: String): OfflinePlayer? {
        for (player in Bukkit.getOfflinePlayers()) {
            if (player.name == name) return player
        }
        return null
    }

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
                    if (player.hasPermission("advancedservermanager.admin.survival")) {
                        player.gameMode = GameMode.SURVIVAL
                        player.sendMessage(getMessage("Survival"))
                    } else {
                        player.sendMessage(getMessage("Permissions"))
                    }
                } else if ((args[0] == "1") || (args[0].equals("creative", ignoreCase = true))) {
                    if (player.hasPermission("advancedservermanager.admin.creative")) {
                        player.gameMode = GameMode.CREATIVE
                        player.sendMessage(getMessage("Creative"))
                    } else {
                        player.sendMessage(getMessage("Permissions"))
                    }
                } else if ((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true))) {
                    if (player.hasPermission("advancedservermanager.admin.adventure")) {
                        player.gameMode = GameMode.ADVENTURE
                        player.sendMessage(getMessage("Adventure"))
                    } else {
                        player.sendMessage(getMessage("Permissions"))
                    }
                } else if ((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true))) {
                    if (player.hasPermission("advancedservermanager.admin.spectator")) {
                        player.gameMode = GameMode.SPECTATOR
                        player.sendMessage(getMessage("Spectator"))
                    } else {
                        player.sendMessage(getMessage("Permissions"))
                    }
                }
            } else if (args.size == 2) {
                if (((args[0] == "0") || (args[0].equals("survival", ignoreCase = true))) &&
                    (args[1] == getOnlinePlayer()?.displayName)
                ) {
                    if (player.hasPermission("advancedservermanager.admin.survival")) {
                        getOnlinePlayer()?.gameMode = GameMode.SURVIVAL
                        player.sendMessage(getMessage("Survival"))
                    } else {
                        player.sendMessage(getMessage("Permissions"))
                    }
                } else if (((args[0] == "1") || (args[0].equals("creative", ignoreCase = true))) &&
                    (args[1] == getOnlinePlayer()?.displayName)
                ) {
                    if (player.hasPermission("advancedservermanager.admin.creative")) {
                        getOnlinePlayer()?.gameMode = GameMode.CREATIVE
                        player.sendMessage(getMessage("Creative"))
                    } else {
                        player.sendMessage(getMessage("Permissions"))
                    }
                } else if (((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true))) &&
                    (args[1] == getOnlinePlayer()?.displayName)
                ) {
                    if (player.hasPermission("advancedservermanager.admin.adventure")) {
                        getOnlinePlayer()?.gameMode = GameMode.ADVENTURE
                        player.sendMessage(getMessage("Adventure"))
                    } else {
                        player.sendMessage(getMessage("Permissions"))
                    }
                } else if (((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true))) &&
                    (args[1] == getOnlinePlayer()?.displayName)
                ) {
                    if (player.hasPermission("advancedservermanager.admin.spectator")) {
                        getOnlinePlayer()?.gameMode = GameMode.SPECTATOR
                        player.sendMessage(getMessage("Spectator"))
                    } else {
                        player.sendMessage(getMessage("Permissions"))
                    }
                } else if (getOfflinePlayer(args[1]) == null) {
                    player.sendMessage(getMessage("OfflineOrDoesNotExist"))
                }
            }
        }
        if (sender is ConsoleCommandSender) {
            if (args.size == 1) {
                println("\u001b[31mPlease provide a player to change gamemode: \u001b[32m/gm [gamemode/0-3]\u001b[0m")
            } else if (args.size == 2) {
                if (((args[0] == "0") || (args[0].equals("survival", ignoreCase = true))) &&
                    (args[1] == getOnlinePlayer()?.displayName)
                ) {
                    getOnlinePlayer()?.gameMode = GameMode.SURVIVAL
                    println("\u001B[32mGamemode changed for ${args[1]} to Survival\u001B[0m")
                } else if (((args[0] == "1") || (args[0].equals("creative", ignoreCase = true))) &&
                    (args[1] == getOnlinePlayer()?.displayName)
                ) {
                    getOnlinePlayer()?.gameMode = GameMode.CREATIVE
                    println("\u001B[32mGamemode changed for ${args[1]} to Creative\u001B[0m")
                } else if (((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true))) &&
                    (args[1] == getOnlinePlayer()?.displayName)
                ) {
                    getOnlinePlayer()?.gameMode = GameMode.ADVENTURE
                    println("\u001B[32mGamemode changed for ${args[1]} to Adventure\u001B[0m")
                } else if (((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true))) &&
                    (args[1] == getOnlinePlayer()?.displayName)
                ) {
                    getOnlinePlayer()?.gameMode = GameMode.SPECTATOR
                    println("\u001B[32mGamemode changed for ${args[1]} to Spectator\u001B[0m")
                } else if (getOfflinePlayer(args[1]) == null) {
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