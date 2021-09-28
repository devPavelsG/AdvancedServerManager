package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.*
import org.bukkit.entity.Player

class GodCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player: Player = sender
            if (player.hasPermission("advancedservermanager.god")) {
                if ((player.gameMode == GameMode.SURVIVAL) && (args.isEmpty())) {
                    if (player.isInvulnerable) {
                        player.isInvulnerable = false
                        player.sendMessage(plugin.getConfigMessage("GodModeOff"))
                    } else {
                        player.isInvulnerable = true
                        player.sendMessage(plugin.getConfigMessage("GodModeOn"))
                    }
                } else if (((args.size == 1) && plugin.getSingleOnlinePlayer(args[0])?.gameMode == GameMode.SURVIVAL)) {
                    if ((args[0] == plugin.getSingleOnlinePlayer(args[0])?.displayName) &&
                        (plugin.getSingleOnlinePlayer(args[0])?.isInvulnerable == true)
                    ) {
                        plugin.getSingleOnlinePlayer(args[0])?.isInvulnerable = false
                        plugin.getSingleOnlinePlayer(args[0])?.sendMessage(plugin.getConfigMessage("GodModeOffPlayer"))
                    } else {
                        plugin.getSingleOnlinePlayer(args[0])?.isInvulnerable = true
                        plugin.getSingleOnlinePlayer(args[0])?.sendMessage(plugin.getConfigMessage("GodModeOnPlayer"))
                    }
                }
            }
        }
        if (sender is ConsoleCommandSender) {
            if (args.isEmpty()) {
                println("\u001b[31mPlease provide a player: \u001B[32m/god [player]\u001B[0m")
            } else if (((args.size == 1) && plugin.getSingleOnlinePlayer(args[0])?.gameMode == GameMode.SURVIVAL)) {
                if ((args[0] == plugin.getSingleOnlinePlayer(args[0])?.displayName) &&
                    (plugin.getSingleOnlinePlayer(args[0])?.isInvulnerable == true)
                ) {
                    plugin.getSingleOnlinePlayer(args[0])?.isInvulnerable = false
                    println("\u001B[32mGod Mode turned OFF for ${args[0]}\u001B[0m")
                } else if ((args[0] == plugin.getSingleOnlinePlayer(args[0])?.displayName) &&
                    (plugin.getSingleOnlinePlayer(args[0])?.isInvulnerable == false)
                ) {
                    plugin.getSingleOnlinePlayer(args[0])?.isInvulnerable = true
                    println("\u001B[32mGod Mode turned ON for ${args[0]}\u001B[0m")
                } else if (plugin.checkOfflinePlayer(args[0]) == null) {
                    println("\u001b[31mPlayer is offline or does not exist!\u001b[0m")
                }
            } else {
                println("\u001B[31mSwitch player to Survival to turn God Mode ON or OFF\u001B[0m")
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