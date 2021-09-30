package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class SetTimeCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        try {
            if (args.size == 1 && args[0].equals("day", ignoreCase = true)) {
                plugin.ifPermissible(sender, "advancedservermanager.time", {
                    if (plugin.world != null) {
                        plugin.world.time = 1000
                        sender.sendMessage(plugin.getConfigMessage("DayTimeCommand"))
                    } else {
                        sender.sendMessage(plugin.getConfigMessage("SomethingWentWrong"))
                    }
                }, false)
            } else if (args.size == 1 && args[0].equals("night", ignoreCase = true)) {
                plugin.ifPermissible(sender, "advancedservermanager.time", {
                    if (plugin.world != null) {
                        plugin.world.time = 18000
                        sender.sendMessage(plugin.getConfigMessage("NightTimeCommand"))
                    } else {
                        sender.sendMessage(plugin.getConfigMessage("SomethingWentWrong"))
                    }
                }, false)
                return true
            } else if (args[0].toLong() > 0 && args[0].toLong() < Long.MAX_VALUE) {
                plugin.ifPermissible(sender, "advancedservermanager.time", {
                    if (plugin.world != null) {
                        plugin.world.time = args[0].toLong()
                        sender.sendMessage(plugin.getConfigMessage("NumberTimeCommand") + " ${ChatColor.GOLD}${args[0]}")
                    } else {
                        sender.sendMessage(plugin.getConfigMessage("SomethingWentWrong"))
                    }
                }, false)
            } else if (args[0].contains("-")) {
                sender.sendMessage(plugin.getConfigMessage("UnknownTime"))
            }
        } catch (e: NumberFormatException) {
            sender.sendMessage(plugin.getConfigMessage("UnknownTime"))
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
            completions.add("day")
            completions.add("night")
        }
        return completions
    }
}