package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.*
import org.bukkit.entity.Player

class FlyCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (args.isEmpty()) {
            plugin.ifPermissible(sender, "advancedservermanager.fly", {
                val player: Player = sender as Player
                if (player.gameMode == GameMode.SURVIVAL) {
                    if (!player.isFlying && !player.allowFlight) {
                        player.allowFlight = true
                        player.isFlying = true
                        player.sendMessage(plugin.getConfigMessage("FlyOn"))
                    } else {
                        player.allowFlight = false
                        player.isFlying = false
                        player.sendMessage(plugin.getConfigMessage("FlyOff"))
                    }
                } else {
                    player.sendMessage(plugin.getConfigMessage("FlyNotSurvival"))
                }
            }, true)
            return true
        } else if(args.size == 1) {
            plugin.ifPermissible(sender, "advancedservermanager.fly.players", {
                plugin.getOnlinePlayer(args[0]).ifPresentOrElse({
                    if (it.gameMode == GameMode.SURVIVAL) {
                        if (!it.isFlying && !it.allowFlight) {
                            it.allowFlight = true
                            it.isFlying = true
                            sender.sendMessage(plugin.getConfigMessage("FlyOn"))
                        } else {
                            it.allowFlight = false
                            it.isFlying = false
                            sender.sendMessage(plugin.getConfigMessage("FlyOff"))
                        }
                    } else {
                        sender.sendMessage(plugin.getConfigMessage("FlyNotSurvival"))
                    }
                }, {
                    sender.sendMessage(plugin.getConfigMessage("OfflineOrDoesNotExist"))
                })
            })
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
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.displayName)
            }
        }
        return completions
    }
}