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
import org.bukkit.util.Vector
import java.io.File


class HomeCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.home", {
                val player: Player = sender as Player
                try {
                    val playerUUID = player.uniqueId.toString()
                    val file = File(plugin.dataFolder, "homes.yml")
                    val homes = YamlConfiguration.loadConfiguration(file)

                    val x = homes.get("$playerUUID.x")
                    val y = homes.get("$playerUUID.y")
                    val z = homes.get("$playerUUID.z")
                    val pitch = homes.get("$playerUUID.pitch")
                    val yaw = homes.get("$playerUUID.yaw")
                    val playerWorld = homes.get("$playerUUID.world")

                    val world = Bukkit.getServer().getWorld(playerWorld.toString())
                    val loc = Location(world, (x as Int).toDouble(), (y as Int).toDouble(), (z as Int).toDouble(),
                        (yaw as Double).toFloat(), (pitch as Double).toFloat()
                    )

                    player.teleport(loc)
                    sender.sendMessage(getConfigMessage("GoHome"))
                } catch (e: NullPointerException) {
                    sender.sendMessage(getConfigMessage("HomeDoesNotExist"))
                }
            }, true)
            return true
        } else if (args.size == 1) {
            ifPermissible(sender, "advancedservermanager.home.players", {
                val player: Player = sender as Player
                try {
                    val playerUUID = Bukkit.getPlayer(args[0])?.uniqueId.toString()
                    val file = File(plugin.dataFolder, "homes.yml")
                    val homes = YamlConfiguration.loadConfiguration(file)

                    val x = homes.get("$playerUUID.x")
                    val y = homes.get("$playerUUID.y")
                    val z = homes.get("$playerUUID.z")
                    val pitch = homes.get("$playerUUID.pitch")
                    val yaw = homes.get("$playerUUID.yaw")
                    val playerWorld = homes.get("$playerUUID.world")

                    val world = Bukkit.getServer().getWorld(playerWorld.toString())
                    val loc = Location(world, (x as Int).toDouble(), (y as Int).toDouble(), (z as Int).toDouble(),
                        (yaw as Double).toFloat(), (pitch as Double).toFloat())

                    player.teleport(loc)

                    if (getConfigMessage("GoHomePlayers").contains("%player_name%")) {
                        val message = getConfigMessage("GoHomePlayers").replace("%player_name%", args[0])
                        sender.sendMessage(message)
                    } else {
                        sender.sendMessage(getConfigMessage("GoHomePlayers"))
                    }
                } catch (e: NullPointerException) {
                    if (getConfigMessage("HomeDoesNotExistOthers").contains("%player_name%")) {
                        val message = getConfigMessage("HomeDoesNotExistOthers").replace("%player_name%", args[0])
                        sender.sendMessage(message)
                    } else sender.sendMessage(getConfigMessage("SomethingWentWrong"))
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