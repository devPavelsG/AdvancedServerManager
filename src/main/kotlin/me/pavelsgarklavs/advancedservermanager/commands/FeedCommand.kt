package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.entity.Player

class FeedCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) {
            plugin.ifPermissible(sender, "advancedservermanager.feed", {
                val player: Player = sender as Player
                player.foodLevel = 20
                sender.sendMessage(plugin.getConfigMessage("Feed"))
            }, true)
            return true
        } else if(args.size == 1) {
            plugin.ifPermissible(sender, "advancedservermanager.feed.players", {
                plugin.getOnlinePlayer(args[0]).ifPresentOrElse({
                    it.foodLevel = 20
                    sender.sendMessage(plugin.getConfigMessage("Feed"))
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
                completions.add(onlinePlayer.name)
            }
        }
        return completions
    }
}