package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.*
import org.bukkit.entity.Player

class FlyCommand(private val plugin: AdvancedServerManager) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender is Player) {
            val player: Player = sender

            if (player.hasPermission("advancedservermanager.admin.fly")) {
                if (args.isEmpty()) {
                    if (player.gameMode == GameMode.SURVIVAL) {
                        if (!player.isFlying && !player.allowFlight) {
                            player.allowFlight = true
                            player.isFlying = true
                            player.sendMessage(plugin.getConfigMessage("FlyOn"))
                        } else {
                            player.allowFlight = false
                            player.isFlying = false
                            player.sendMessage(plugin.getConfigMessage("FlyOff"))
                        }
                    } else {
                        player.sendMessage(plugin.getConfigMessage("FlyNotSurvival"))
                    }
                } else if ((args.size == 1) && player.hasPermission("advancedservermanager.admin.fly.players")) {
                    if (args[0] == plugin.getSingleOnlinePlayer()?.displayName) {
                        if (plugin.getSingleOnlinePlayer()!!.gameMode == GameMode.SURVIVAL) {
                            if (!plugin.getSingleOnlinePlayer()!!.isFlying && !plugin.getSingleOnlinePlayer()!!.allowFlight) {
                                plugin.getSingleOnlinePlayer()!!.allowFlight = true
                                plugin.getSingleOnlinePlayer()!!.isFlying = true
                                player.sendMessage(plugin.getConfigMessage("FlyOn"))
                            } else {
                                plugin.getSingleOnlinePlayer()!!.allowFlight = false
                                plugin.getSingleOnlinePlayer()!!.isFlying = false
                                player.sendMessage(plugin.getConfigMessage("FlyOff"))
                            }
                        } else {
                            player.sendMessage(plugin.getConfigMessage("FlyNotSurvival"))
                        }
                    } else if (plugin.checkOfflinePlayer(args[0]) == null) {
                        player.sendMessage(plugin.getConfigMessage("OfflineOrDoesNotExist"))
                    }
                } else {
                    player.sendMessage(plugin.getConfigMessage("Permissions"))
                }
            } else {
                player.sendMessage(plugin.getConfigMessage("Permissions"))
            }
        }
        if (sender is ConsoleCommandSender) {
            if (args.isEmpty()) {
                println("\u001b[31mPlease provide a player to turn ON or OFF\u001B[32m: /fly [player]\u001b[0m")
            } else if (args.size == 1) {
                if (args[0] == plugin.getSingleOnlinePlayer()?.displayName) {
                    if (plugin.getSingleOnlinePlayer()!!.gameMode == GameMode.SURVIVAL) {
                        if (!plugin.getSingleOnlinePlayer()!!.isFlying && !plugin.getSingleOnlinePlayer()!!.allowFlight) {
                            plugin.getSingleOnlinePlayer()!!.allowFlight = true
                            plugin.getSingleOnlinePlayer()!!.isFlying = true
                            println("\u001B[32mFly turned ON for ${args[0]}\u001B[0m")
                        } else {
                            plugin.getSingleOnlinePlayer()!!.allowFlight = false
                            plugin.getSingleOnlinePlayer()!!.isFlying = false
                            println("\u001B[32mFly turned OFF for \u001B[32m${args[0]}\u001B[0m")
                        }
                    } else {
                        println("\u001B[32mSwitch To Survival to turn \u001B[32mON \u001B[32mor \u001B[31mOFF \u001B[32mfly\u001B[0m")
                    }
                } else if (plugin.checkOfflinePlayer(args[0]) == null) {
                    println("\u001b[31mPlayer is offline or does not exist!\u001b[0m")
                }
            }
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
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.displayName)
            }
        }
        return completions
    }
}