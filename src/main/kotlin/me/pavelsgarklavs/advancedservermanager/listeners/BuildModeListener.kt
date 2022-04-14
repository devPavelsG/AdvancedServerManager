package me.pavelsgarklavs.advancedservermanager.listeners

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.GameMode
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.player.PlayerInteractEvent
import java.io.File

class BuildModeListener(plugin: AdvancedServerManager) : Listener, Utils(plugin) {

    @EventHandler
    fun breakBlockEvent(breakEvent: PlayerInteractEvent) {
        val player = breakEvent.player
        if ((breakEvent.action == Action.LEFT_CLICK_BLOCK || breakEvent.action == Action.RIGHT_CLICK_BLOCK) && player.gameMode == GameMode.CREATIVE) {
            try {
                val playerUUID = player.uniqueId.toString()
                val file = File(plugin.dataFolder, "players.yml")
                if (!file.exists()) {
                    file.createNewFile()
                }
                val config = YamlConfiguration.loadConfiguration(file)
                if (!config.contains(playerUUID)) config.set("$playerUUID.buildmode", true)

                val isInBuildMode = config.get("$playerUUID.buildmode") as Boolean
                config.save(file)

                if (isInBuildMode) player.sendMessage(getConfigMessage("BuildModeWhenOn"))

                if (isInBuildMode) {
                    breakEvent.setUseInteractedBlock(Event.Result.DENY)
                } else breakEvent.setUseInteractedBlock(Event.Result.ALLOW)
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
        }
    }

    @EventHandler
    fun gameModeSwitchEvent(switchEvent: PlayerGameModeChangeEvent) {
        val player = switchEvent.player
        try {
            val playerUUID = player.uniqueId.toString()
            val file = File(plugin.dataFolder, "players.yml")
            if (!file.exists()) file.createNewFile()

            val config = YamlConfiguration.loadConfiguration(file)

            if (switchEvent.newGameMode == GameMode.CREATIVE) config.set("$playerUUID.buildmode", true)
            config.save(file)
        } catch (e: Exception) {
            println(e.printStackTrace())
        }
    }
}