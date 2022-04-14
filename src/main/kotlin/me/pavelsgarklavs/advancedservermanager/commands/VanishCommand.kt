package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class VanishCommand(plugin: AdvancedServerManager) : CommandExecutor, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as Player
        ifPermissible(sender, "advancedservermanager.vanish", {
            try {
                val playerUUID = player.uniqueId.toString()
                val file = File(plugin.dataFolder, "players.yml")

                if (!file.exists()) file.createNewFile()

                val config = YamlConfiguration.loadConfiguration(file)
                if (!config.contains(playerUUID)) config.set("$playerUUID.vanish", true)

                val isVanish = config.getBoolean("$playerUUID.vanish")

                if (isVanish) {
                    for (allPlayers in Bukkit.getOnlinePlayers()) {
                        allPlayers.hidePlayer(plugin, player)
                    }
                    config.set("$playerUUID.vanish", false)
                    player.sendMessage(getConfigMessage("VanishOn"))
                } else {
                    for (allPlayers in Bukkit.getOnlinePlayers()) {
                        allPlayers.showPlayer(plugin, player)
                    }
                    config.set("$playerUUID.vanish", true)
                    player.sendMessage(getConfigMessage("VanishOff"))
                }

                config.save(file)
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
        }, true)
        return true
    }
}