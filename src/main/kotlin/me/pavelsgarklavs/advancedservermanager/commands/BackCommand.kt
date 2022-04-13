package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class BackCommand(plugin: AdvancedServerManager) : CommandExecutor, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.back", {
                val player: Player = sender as Player
                try {
                    val playerUUID = player.uniqueId.toString()
                    val file = File(plugin.dataFolder, "lastdeath.yml")
                    val homes = YamlConfiguration.loadConfiguration(file)

                    val x = homes.get("$playerUUID.x")
                    val y = homes.get("$playerUUID.y")
                    val z = homes.get("$playerUUID.z")
                    val pitch = homes.get("$playerUUID.pitch")
                    val yaw = homes.get("$playerUUID.yaw")
                    val playerWorld = homes.get("$playerUUID.world")

                    val world = Bukkit.getServer().getWorld(playerWorld.toString())
                    val loc = Location(
                        world, (x as Int).toDouble(), (y as Int).toDouble(), (z as Int).toDouble(),
                        (yaw as Double).toFloat(), (pitch as Double).toFloat()
                    )
                    player.teleport(loc)
                    sender.sendMessage(getConfigMessage("GoBack"))
                } catch (e: NullPointerException) {
                    sender.sendMessage(getConfigMessage("BackDoesNotExist"))
                }
            }, true)
            return true
        } else sender.sendMessage(getConfigMessage("ErrorArguments"))
        return false
    }
}