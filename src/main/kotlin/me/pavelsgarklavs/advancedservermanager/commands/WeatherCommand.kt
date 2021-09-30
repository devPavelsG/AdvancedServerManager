package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class WeatherCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size == 1 && args[0].equals("clear", ignoreCase = true)) {
            plugin.ifPermissible(sender, "advancedservermanager.weather", {
                if (plugin.world != null) {
                    plugin.world.clearWeatherDuration = ((0..599).random()) * 20
                    sender.sendMessage(plugin.getConfigMessage("ClearWeatherCommand"))
                } else {
                    sender.sendMessage(plugin.getConfigMessage("SomethingWentWrong"))
                }
            }, false)
        } else if (args.size == 1 && args[0].equals("rain", ignoreCase = true)) {
            plugin.ifPermissible(sender, "advancedservermanager.weather", {
                if (plugin.world != null) {
                    plugin.world.setStorm(true)
                    sender.sendMessage(plugin.getConfigMessage("RainWeatherCommand"))
                } else {
                    sender.sendMessage(plugin.getConfigMessage("SomethingWentWrong"))
                }
            }, false)
            return true
        } else if (args.isEmpty() || args[0] != "clear" || args[0] != "rain") {
            sender.sendMessage(plugin.getConfigMessage("UnknownWeather"))
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
            completions.add("clear")
            completions.add("rain")
        }
        return completions
    }
}