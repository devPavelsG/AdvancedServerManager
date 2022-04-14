package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TeleportCommand(plugin: AdvancedServerManager) : CommandExecutor, TabCompleter, Utils(plugin) {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size == 1) {
            ifPermissible(sender, "advancedservermanager.tp", {
                val playerSender: Player = sender as Player
                getOnlinePlayer(args[0]).ifPresentOrElse({
                    val player = Bukkit.getPlayer(args[0])

                    if (player != null) playerSender.teleport(player.location)

                    if (getConfigMessage("TeleportTo").contains("%player_name%")) {
                        val message = getConfigMessage("TeleportTo").replace("%player_name%", args[0])
                        sender.sendMessage(message)
                    } else sender.sendMessage(getConfigMessage("TeleportTo"))
                }, {
                    sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
                })
            }, true)
            return true
        } else if (args.size == 2) {
            ifPermissible(sender, "advancedservermanager.tp.others", {
                getOnlinePlayer(args[0]).ifPresentOrElse({
                    val player = Bukkit.getPlayer(args[0])
                    val teleportToPlayer = Bukkit.getPlayer(args[1])

                    if (player != null && teleportToPlayer != null) {
                        player.teleport(teleportToPlayer.location)

                        if (getConfigMessage("TeleportOther").contains("%player_name%") &&
                            getConfigMessage("TeleportOther").contains("%player_name_other%")
                        ) {
                            val message = getConfigMessage("TeleportOther").replace("%player_name%", args[0])
                            val fullMessage = message.replace("%player_name_other%", args[1])
                            sender.sendMessage(fullMessage)
                        } else sender.sendMessage(getConfigMessage("TeleportOther"))
                    } else if (teleportToPlayer == null) sender.sendMessage(getConfigMessage("OtherPlayerIsNotOnlineOrDoesNotExist"))
                }, {
                    sender.sendMessage(getConfigMessage("OfflineOrDoesNotExist"))
                })
            }, true)
            return true
        } else if (args.size >= 3) {
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
        if (args.size <= 2) {
            for (OnlinePlayer in Bukkit.getOnlinePlayers()) {
                OnlinePlayer.name.let { completions.add(it) }
            }
        }
        return completions
    }
}