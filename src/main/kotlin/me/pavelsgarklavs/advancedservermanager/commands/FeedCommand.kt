package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class FeedCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.feed", {
                val player: Player = sender as Player
                player.foodLevel = 20
                sender.sendMessage(getConfigMessage("Feed"))
            }, true)
            return true
        } else if (args.size == 1) {
            ifPermissible(sender, "advancedservermanager.feed.players", {
                getOnlinePlayer(args[0]).ifPresentOrElse({
                    it.foodLevel = 20
                    if (getConfigMessage("FeedOthers").contains("%player_name%")) {
                        val message = getConfigMessage("FeedOthers").replace("%player_name%", args[0])
                        sender.sendMessage(message)
                    } else {
                        sender.sendMessage(getConfigMessage("FeedOthers"))
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
                completions.add(onlinePlayer.name)
            }
        }
        return completions
    }
}