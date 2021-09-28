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
        if (args.size == 1) {
            if ((args[0] == "0") || (args[0].equals("survival", ignoreCase = true))) {
                plugin.ifPermissible(sender, "advancedservermanager.survival", {
                    val player: Player = sender as Player
                    player.gameMode = GameMode.SURVIVAL
                    sender.sendMessage(plugin.getConfigMessage("Survival"))
                })
            } else if ((args[0] == "1") || (args[0].equals("creative", ignoreCase = true))) {
                plugin.ifPermissible(sender, "advancedservermanager.creative", {
                    val player: Player = sender as Player
                    player.gameMode = GameMode.CREATIVE
                    sender.sendMessage(plugin.getConfigMessage("Creative"))
                })
            } else if ((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true))) {
                plugin.ifPermissible(sender, "advancedservermanager.adventure", {
                    val player: Player = sender as Player
                    player.gameMode = GameMode.ADVENTURE
                    sender.sendMessage(plugin.getConfigMessage("Adventure"))
                })
            }
            return true
        } else if (args.size == 2) {
            if (((args[0] == "0") || (args[0].equals("survival", ignoreCase = true)))) {
                plugin.ifPermissible(sender, "advancedservermanager.survival.players", {
                    plugin.getOnlinePlayer(args[1]).ifPresentOrElse({
                        it.gameMode = GameMode.SURVIVAL
                        sender.sendMessage(plugin.getConfigMessage("Survival"))
                    }, {
                        sender.sendMessage(plugin.getConfigMessage("OfflineOrDoesNotExist"))
                    })
                })
            } else if (((args[0] == "1") || (args[0].equals("creative", ignoreCase = true)))) {
                plugin.ifPermissible(sender, "advancedservermanager.creative.players", {
                    plugin.getOnlinePlayer(args[1]).ifPresentOrElse({
                        it.gameMode = GameMode.CREATIVE
                        sender.sendMessage(plugin.getConfigMessage("Creative"))
                    }, {
                        sender.sendMessage(plugin.getConfigMessage("OfflineOrDoesNotExist"))
                    })
                })
            } else if (((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true)))) {
                plugin.ifPermissible(sender, "advancedservermanager.adventure.players", {
                    plugin.getOnlinePlayer(args[1]).ifPresentOrElse({
                        it.gameMode = GameMode.ADVENTURE
                        sender.sendMessage(plugin.getConfigMessage("Adventure"))
                    }, {
                        sender.sendMessage(plugin.getConfigMessage("OfflineOrDoesNotExist"))
                    })
                })
            } else if (((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true)))) {
                plugin.ifPermissible(sender, "advancedservermanager.spectator.players", {
                    plugin.getOnlinePlayer(args[1]).ifPresentOrElse({
                        it.gameMode = GameMode.SPECTATOR
                        sender.sendMessage(plugin.getConfigMessage("Spectator"))
                    }, {
                        sender.sendMessage(plugin.getConfigMessage("OfflineOrDoesNotExist"))
                    })
                })
            } else {
                sender.sendMessage(plugin.getConfigMessage("UnknownGamemode"))
            }
            return true
        }

        return false
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