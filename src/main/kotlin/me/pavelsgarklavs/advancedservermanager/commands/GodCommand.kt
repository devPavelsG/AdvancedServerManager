package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.entity.Player

class GodCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.god", {
                val player: Player = sender as Player
                if (player.isInvulnerable) {
                    player.isInvulnerable = false
                    sender.sendMessage(getConfigMessage("GodModeOff"))
                } else {
                    player.isInvulnerable = true
                    sender.sendMessage(getConfigMessage("GodModeOn"))
                }
            }, true)
            return true
        } else if(args.size == 1) {
            ifPermissible(sender, "advancedservermanager.god.players", {
                getOnlinePlayer(args[0]).ifPresentOrElse({
                    if (it.isInvulnerable) {
                        it.isInvulnerable = false
                        sender.sendMessage(getConfigMessage("GodModeOffPlayer"))
                    } else {
                        it.isInvulnerable = true
                        sender.sendMessage(getConfigMessage("GodModeOnPlayer"))
                    }
                }, {
                    sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
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