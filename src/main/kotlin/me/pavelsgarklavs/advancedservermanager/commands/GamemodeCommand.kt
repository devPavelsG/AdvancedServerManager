package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GamemodeCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size == 1) {
            if ((args[0] == "0") || (args[0].equals("survival", ignoreCase = true))) {
                ifPermissible(sender, "advancedservermanager.survival", {
                    val player: Player = sender as Player
                    player.gameMode = GameMode.SURVIVAL
                    sender.sendMessage(getConfigMessage("Survival"))
                }, true)
            } else if ((args[0] == "1") || (args[0].equals("creative", ignoreCase = true))) {
                ifPermissible(sender, "advancedservermanager.creative", {
                    val player: Player = sender as Player
                    player.gameMode = GameMode.CREATIVE
                    sender.sendMessage(getConfigMessage("Creative"))
                }, true)
            } else if ((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true))) {
                ifPermissible(sender, "advancedservermanager.adventure", {
                    val player: Player = sender as Player
                    player.gameMode = GameMode.ADVENTURE
                    sender.sendMessage(getConfigMessage("Adventure"))
                }, true)
            } else if ((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true))) {
                ifPermissible(sender, "advancedservermanager.spectator", {
                    val player: Player = sender as Player
                    player.gameMode = GameMode.SPECTATOR
                    sender.sendMessage(getConfigMessage("Spectator"))
                }, true)
            }
            return true
        } else if (args.size == 2) {
            if (((args[0] == "0") || (args[0].equals("survival", ignoreCase = true)))) {
                ifPermissible(sender, "advancedservermanager.survival.players", {
                    getOnlinePlayer(args[1]).ifPresentOrElse({
                        it.gameMode = GameMode.SURVIVAL
                        sender.sendMessage(getConfigMessage("Survival"))
                    }, {
                        sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
                    })
                }, false)
            } else if (((args[0] == "1") || (args[0].equals("creative", ignoreCase = true)))) {
                ifPermissible(sender, "advancedservermanager.creative.players", {
                    getOnlinePlayer(args[1]).ifPresentOrElse({
                        it.gameMode = GameMode.CREATIVE
                        sender.sendMessage(getConfigMessage("Creative"))
                    }, {
                        sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
                    })
                }, false)
            } else if (((args[0] == "2") || (args[0].equals("adventure", ignoreCase = true)))) {
                ifPermissible(sender, "advancedservermanager.adventure.players", {
                    getOnlinePlayer(args[1]).ifPresentOrElse({
                        it.gameMode = GameMode.ADVENTURE
                        sender.sendMessage(getConfigMessage("Adventure"))
                    }, {
                        sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
                    })
                }, false)
            } else if (((args[0] == "3") || (args[0].equals("spectator", ignoreCase = true)))) {
                ifPermissible(sender, "advancedservermanager.spectator.players", {
                    getOnlinePlayer(args[1]).ifPresentOrElse({
                        it.gameMode = GameMode.SPECTATOR
                        sender.sendMessage(getConfigMessage("Spectator"))
                    }, {
                        sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
                    })
                }, false)
            } else {
                sender.sendMessage(getConfigMessage("UnknownGamemode"))
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