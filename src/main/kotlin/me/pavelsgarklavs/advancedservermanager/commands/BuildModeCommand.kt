package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class BuildModeCommand(plugin: AdvancedServerManager) : CommandExecutor, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.buildmode", {
                val player: Player = sender as Player
                try {
                    val playerUUID = player.uniqueId.toString()
                    val file = File(plugin.dataFolder, "players.yml")
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    val config = YamlConfiguration.loadConfiguration(file)
                    if (!config.contains(playerUUID)) {
                        config.set("$playerUUID.buildmode", true)
                    }

                    val isInBuildMode = config.getBoolean("$playerUUID.buildmode")

                    if (isInBuildMode && player.gameMode == GameMode.CREATIVE) {
                        player.sendMessage(getConfigMessage("BuildModeOff"))
                        config.set("$playerUUID.buildmode", false)
                    } else if (!isInBuildMode && player.gameMode == GameMode.CREATIVE) {
                        player.sendMessage(getConfigMessage("BuildModeOn"))
                        config.set("$playerUUID.buildmode", true)
                    } else player.sendMessage(getConfigMessage("BuildModeWhenNotCreative"))
                    config.save(file)
                } catch (e: Exception) {
                    e.printStackTrace()
                    sender.sendMessage(getConfigMessage("SomethingWentWrong"))
                }
            }, true)
            return true
        } else {
            ifPermissible(sender, "advancedservermanager.buildmode", {
                sender.sendMessage(getConfigMessage("ErrorArguments"))
            })
            return false
        }
    }
}