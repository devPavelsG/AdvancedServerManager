package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.*
import org.bukkit.entity.Player

class GodCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) {
            plugin.ifPermissible(sender, "advancedservermanager.god", {
                val player: Player = sender as Player
                if (player.isInvulnerable) {
                    player.isInvulnerable = false
                    sender.sendMessage(plugin.getConfigMessage("GodModeOff"))
                } else {
                    player.isInvulnerable = true
                    sender.sendMessage(plugin.getConfigMessage("GodModeOn"))
                }
            }, true)
            return true
        } else if(args.size == 1) {
            plugin.ifPermissible(sender, "advancedservermanager.god.players", {
                plugin.getOnlinePlayer(args[0]).ifPresentOrElse({
                    if (it.isInvulnerable) {
                        it.isInvulnerable = false
                        sender.sendMessage(plugin.getConfigMessage("GodModeOffPlayer"))
                    } else {
                        it.isInvulnerable = true
                        sender.sendMessage(plugin.getConfigMessage("GodModeOnPlayer"))
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