package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TimeCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        try {
            if (args.size == 1 && args[0].equals("day", ignoreCase = true)) {
                ifPermissible(sender, "advancedservermanager.time", {
                    getOverWorld()?.time = 1000
                    sender.sendMessage(getConfigMessage("DayTimeCommand"))
                }, false)
                return true
            } else if (args.size == 1 && args[0].equals("night", ignoreCase = true)) {
                ifPermissible(sender, "advancedservermanager.time", {
                    getOverWorld()?.time = 18000
                    sender.sendMessage(getConfigMessage("NightTimeCommand"))
                }, false)
                return true
            } else if (args.size == 1 && (args[0].toLong() > 0 && args[0].toLong() < Long.MAX_VALUE)) {
                ifPermissible(sender, "advancedservermanager.time", {
                    getOverWorld()?.time = args[0].toLong()
                    sender.sendMessage(getConfigMessage("NumberTimeCommand") + " ${ChatColor.GOLD}${args[0]}")
                }, false)
                return true
            } else if (args.size == 1 && args[0].contains("-")) {
                sender.sendMessage(getConfigMessage("UnknownTime"))
            } else if (args.size >= 2) {
                sender.sendMessage(getConfigMessage("ErrorArguments"))
            } else sender.sendMessage(getConfigMessage("AdditionalArgument"))
        } catch (e: NumberFormatException) {
            sender.sendMessage(getConfigMessage("UnknownTime"))
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
            completions.add("day")
            completions.add("night")
        }
        return completions
    }
}