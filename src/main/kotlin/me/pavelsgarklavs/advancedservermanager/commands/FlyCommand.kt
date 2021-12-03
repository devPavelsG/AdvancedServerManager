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

class FlyCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.fly", {
                val player: Player = sender as Player
                if (player.gameMode == GameMode.SURVIVAL) {
                    if (!player.isFlying && !player.allowFlight) {
                        player.allowFlight = true
                        player.isFlying = true
                        player.sendMessage(getConfigMessage("FlyOn"))
                    } else {
                        player.allowFlight = false
                        player.isFlying = false
                        player.sendMessage(getConfigMessage("FlyOff"))
                    }
                } else {
                    player.sendMessage(getConfigMessage("FlyNotSurvival"))
                }
            }, true)
            return true
        } else if (args.size == 1) {
            ifPermissible(sender, "advancedservermanager.fly.players", {
                getOnlinePlayer(args[0]).ifPresentOrElse({
                    if (it.gameMode == GameMode.SURVIVAL) {
                        if (!it.isFlying && !it.allowFlight) {
                            it.allowFlight = true
                            it.isFlying = true
                            sender.sendMessage(getConfigMessage("FlyOn"))
                        } else {
                            it.allowFlight = false
                            it.isFlying = false
                            sender.sendMessage(getConfigMessage("FlyOff"))
                        }
                    } else {
                        sender.sendMessage(getConfigMessage("FlyNotSurvival"))
                    }
                }, {
                    sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
                })
            }, false)
            return true
        } else if (args.size >= 2) {
            sender.sendMessage(getConfigMessage("ErrorArguments"))
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
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.displayName)
            }
        }
        return completions
    }
}