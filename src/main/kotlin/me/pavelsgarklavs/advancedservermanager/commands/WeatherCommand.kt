package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class WeatherCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size == 1 && args[0].equals("clear", ignoreCase = true)) {
            ifPermissible(sender, "advancedservermanager.weather", {
                getOverWorld()?.clearWeatherDuration = ((0..599).random()) * 20
                sender.sendMessage(getConfigMessage("ClearWeatherCommand"))
            }, false)
            return true
        } else if (args.size == 1 && args[0].equals("rain", ignoreCase = true)) {
            ifPermissible(sender, "advancedservermanager.weather", {
                getOverWorld()?.setStorm(true)
                sender.sendMessage(getConfigMessage("RainWeatherCommand"))
            }, false)
            return true
        } else if (args.size >= 2) {
            sender.sendMessage(getConfigMessage("ErrorArguments"))
        } else sender.sendMessage(getConfigMessage("AdditionalArgument"))
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
            completions.add("clear")
            completions.add("rain")
        }
        return completions
    }
}