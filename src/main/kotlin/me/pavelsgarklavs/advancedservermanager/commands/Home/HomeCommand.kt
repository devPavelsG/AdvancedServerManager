package me.pavelsgarklavs.advancedservermanager.commands.Home

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File


class HomeCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.home", {
                val player: Player = sender as Player
                try {
                    val homes: FileConfiguration =
                        YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "homes.yml"))

                    val x = homes.get(player.name + ".x")
                    val y = homes.get(player.name + ".y")
                    val z = homes.get(player.name + ".z")
                    val playerWorld = homes.get(player.name + ".world")

                    val world = Bukkit.getServer().getWorld(playerWorld.toString())
                    val loc = Location(world, (x as Int).toDouble(), (y as Int).toDouble(), (z as Int).toDouble())

                    player.teleport(loc)
                    sender.sendMessage(getConfigMessage("GoHome"))
                } catch (e: Exception) {
                    e.printStackTrace()
                    sender.sendMessage(getConfigMessage("SomethingWentWrong"))
                }
            }, true)
            return true
        } else if (args.size == 1) {
            ifPermissible(sender, "advancedservermanager.home.players", {
                val player: Player = sender as Player
                try {
                    val homes: FileConfiguration =
                        YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "homes.yml"))

                    val x = homes.get(args[0] + ".x")
                    val y = homes.get(args[0] + ".y")
                    val z = homes.get(args[0] + ".z")
                    val playerWorld = homes.get(args[0] + ".world")

                    val world = Bukkit.getServer().getWorld(playerWorld.toString())
                    val loc = Location(world, (x as Int).toDouble(), (y as Int).toDouble(), (z as Int).toDouble())

                    player.teleport(loc)
                    sender.sendMessage(getConfigMessage("GoHome"))
                } catch (e: NullPointerException) {
                    sender.sendMessage(getConfigMessage("HomeDoesNotExist"))
                }
            }, true)
            return true
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
            for (OfflinePlayer in Bukkit.getOfflinePlayers()) {
                OfflinePlayer.name?.let { completions.add(it) }
            }
        }
        return completions
    }
}