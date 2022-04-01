package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSpawnCommand(plugin: AdvancedServerManager) : CommandExecutor, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.setspawn", {
                val player: Player = sender as Player
                val overworld = Bukkit.getWorld("world")
                if (overworld == player.world) {
                    overworld.spawnLocation = player.location
                    player.sendMessage(getConfigMessage("SetSpawn"))
                } else {
                    player.sendMessage(getConfigMessage("InvalidSetSpawnWorld"))
                }
            }, true)
            return true
        } else {
            sender.sendMessage(getConfigMessage("ErrorArguments"))
        }
        return false
    }
}