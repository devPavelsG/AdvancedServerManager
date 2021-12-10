package me.pavelsgarklavs.advancedservermanager.listeners

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent


class StaffChatListener(plugin: AdvancedServerManager) : Listener, Utils(plugin) {
    @EventHandler
    fun staffChatEvent(e: AsyncPlayerChatEvent) {
        val playerMessage = e.message
        val staffSymbol = plugin.config.getString("StaffChatSymbol")
        val staffPrefix =
            plugin.config.getString("StaffChatPrefix")?.let { ChatColor.translateAlternateColorCodes('&', it) }
        val player = e.player
        try {
            if (staffSymbol?.let {
                    playerMessage.startsWith(
                        it,
                        ignoreCase = true
                    )
                } == true && player.hasPermission("advancedservermanager.staffchat")) {
                e.isCancelled = true
                for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("advancedservermanager.staffchat")) {
                        onlinePlayer.sendMessage(
                            "$staffPrefix ${player.name}: ${
                                playerMessage.replaceFirst(
                                    staffSymbol,
                                    "",
                                    ignoreCase = true
                                )
                            }"
                        )
                    }
                }
            }
        } catch (e: NoSuchElementException) {
            println(e.message)
        }
    }
}