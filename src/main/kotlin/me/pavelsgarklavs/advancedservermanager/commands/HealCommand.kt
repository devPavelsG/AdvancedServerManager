package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.entity.Player

class HealCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player: Player = sender

            if ((args.isEmpty()) && player.hasPermission("advancedservermanager.heal")) {
                player.health = 20.0
                player.sendMessage(plugin.getConfigMessage("Heal"))
            } else if ((args.size == 1) && player.hasPermission("advancedservermanager.heal.player")) {
                if (args[0] == plugin.getSingleOnlinePlayer()!!.displayName) {
                    plugin.getSingleOnlinePlayer()!!.health = 20.0
                    player.sendMessage(plugin.getConfigMessage("Heal"))
                }
            } else if (plugin.checkOfflinePlayer(args[0]) == null) {
                player.sendMessage(plugin.getConfigMessage("OfflineOrDoesNotExist"))
            }
        }
        if (sender is ConsoleCommandSender) {
            if (args.isEmpty()) {
                println("\u001b[31mPlease provide a player: \u001B[32m/heal [player]\u001B[0m")
            } else if (args.size == 1) {
                if (args[0] == plugin.getSingleOnlinePlayer()!!.displayName) {
                    plugin.getSingleOnlinePlayer()!!.health = 20.0
                    println("\u001B[32mServer healed ${args[0]}\u001B[0m")
                } else if (plugin.checkOfflinePlayer(args[0]) == null) {
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
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.displayName)
            }
        }
        return completions
    }
}