package me.pavelsgarklavs.advancedservermanager.listeners

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class RespawnListener(plugin: AdvancedServerManager) : Listener, Utils(plugin) {
    @EventHandler
    fun respawnEvent(event: PlayerRespawnEvent) {
        val spawnLocation = Bukkit.getWorld("world")?.spawnLocation
        if (spawnLocation != null && !event.isBedSpawn && !event.isAnchorSpawn) {
            event.respawnLocation = spawnLocation
        }
    }
}