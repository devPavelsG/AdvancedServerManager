package me.pavelsgarklavs.advancedservermanager.listeners

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class OperatorListener(plugin: AdvancedServerManager) : Listener, Utils(plugin) {
    @EventHandler
    fun ifPlayerOp(e: AsyncPlayerChatEvent) {
        val player = e.player
        if (player.isOp) {
            val prefix = plugin.config.getString("OpPrefix")?.let { ChatColor.translateAlternateColorCodes('&', it) }
            val displayName = player.displayName
            player.setDisplayName("${prefix}${displayName}")
        } else player.setDisplayName(player.name)
    }
}