package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class HealCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.heal", {
                val player: Player = sender as Player
                player.health = 20.0
                player.sendMessage(getConfigMessage("Heal"))
            }, true)
            return true
        } else if (args.size == 1) {
            ifPermissible(sender, "advancedservermanager.heal.players", {
                getOnlinePlayer(args[0]).ifPresentOrElse({
                    it.health = 20.0
                    sender.sendMessage(getConfigMessage("Heal"))
                }, {
                    sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
                })
            }, false)
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