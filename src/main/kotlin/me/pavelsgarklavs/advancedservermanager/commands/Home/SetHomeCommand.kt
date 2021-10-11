package me.pavelsgarklavs.advancedservermanager.commands.Home

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File


class SetHomeCommand(plugin: AdvancedServerManager) : CommandExecutor, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.sethome", {
                val player: Player = sender as Player
                try {
                    val file = File(plugin.dataFolder, "homes.yml")
                    val config = YamlConfiguration()
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    config.set(player.name + ".x", player.location.blockX)
                    config.set(player.name + ".y", player.location.blockY)
                    config.set(player.name + ".z", player.location.blockZ)
                    config.set(player.name + ".world", player.world.name)

                    config.save(file)
                    sender.sendMessage(getConfigMessage("SetHome"))
                } catch (e: Exception) {
                    e.printStackTrace()
                    sender.sendMessage(getConfigMessage("SomethingWentWrong"))
                }
            }, true)
            return true
        }
        return true
    }
}