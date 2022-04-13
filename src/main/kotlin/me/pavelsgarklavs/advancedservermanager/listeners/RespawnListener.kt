package me.pavelsgarklavs.advancedservermanager.listeners

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import java.io.File

class RespawnListener(plugin: AdvancedServerManager) : Listener, Utils(plugin) {
    @EventHandler
    fun respawnEvent(event: PlayerRespawnEvent) {
        val spawnLocation = Bukkit.getWorld("world")?.spawnLocation
        if (spawnLocation != null && !event.isBedSpawn && !event.isAnchorSpawn) {
            event.respawnLocation = spawnLocation
        }

        try {
            val playerUUID = event.player.uniqueId.toString()
            val player = event.player
            val file = File(plugin.dataFolder, "lastdeath.yml")
            if (!file.exists()) {
                file.createNewFile()
            }
            val config = YamlConfiguration.loadConfiguration(file)
            config.set("$playerUUID.x", player.location.blockX)
            config.set("$playerUUID.y", player.location.blockY)
            config.set("$playerUUID.z", player.location.blockZ)
            config.set("$playerUUID.pitch", player.location.pitch)
            config.set("$playerUUID.yaw", player.location.yaw)
            config.set("$playerUUID.world", player.world.name)

            config.save(file)
        } catch (e: Exception) {
            e.printStackTrace()
            event.player.sendMessage(getConfigMessage("SomethingWentWrong"))
        }

        ifPermissible(event.player, "advancedservermanager.back", {
            event.player.sendMessage(getConfigMessage("OnDeathMessageGoBack"))
        }, true)

    }
}