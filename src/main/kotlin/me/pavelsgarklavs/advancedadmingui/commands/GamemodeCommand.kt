package me.pavelsgarklavs.advancedadmingui.commands

import me.pavelsgarklavs.advancedadmingui.AdvancedAdminGUI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GamemodeCommand(private val plugin: AdvancedAdminGUI): CommandExecutor, TabCompleter{

    private fun getMessage(path: String): String {
        val prefix = plugin.config.getString("Prefix")
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        val message = plugin.config.getString(path)
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        return prefix + message
    }
    
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
            if (sender is Player) {
                val player: Player = sender

                when (args.size == 1) {
                    args[0] == "0" -> {
                        player.gameMode = GameMode.SURVIVAL
                        player.sendMessage(getMessage("Survival"))
                    }
                    args[0] == "1" -> {
                        player.gameMode = GameMode.CREATIVE
                        player.sendMessage(getMessage("Creative"))
                    }
                    args[0] == "2" -> {
                        player.gameMode = GameMode.ADVENTURE
                        player.sendMessage(getMessage("Adventure"))
                    }
                    args[0] == "3" -> {
                        player.gameMode = GameMode.SPECTATOR
                        player.sendMessage(getMessage("Spectator"))
                    }
                    else -> player.sendMessage("${ChatColor.DARK_RED}Something went wrong!")
                }
            } else {
                println("You must be a player to use this command!")
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
            completions.add("0")
            completions.add("1")
            completions.add("2")
            completions.add("3")
        } else if (args.size == 2) {
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                    completions.add(onlinePlayer.displayName)
            }
        }
        return completions
    }
}