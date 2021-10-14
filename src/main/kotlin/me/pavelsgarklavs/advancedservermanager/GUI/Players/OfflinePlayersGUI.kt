package me.pavelsgarklavs.advancedservermanager.GUI.Players

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.PaginatedGui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class OfflinePlayersGUI(plugin: AdvancedServerManager) : Utils(plugin) {
    private lateinit var playersGUI: PaginatedGui

    init {
        createGUI()
    }

    private fun createGUI() {
        playersGUI = Gui.paginated()
            .title(Component.text("Offline Players", NamedTextColor.GOLD, TextDecoration.BOLD))
            .rows(6)
            .pageSize(45)
            .create()
        playersGUI.setDefaultClickAction { event -> event.isCancelled = true }
    }

    private fun refreshGUI() {
        for (player in Bukkit.getOfflinePlayers()) {
            if (!player.isOnline) {
                val skullItem = player.name?.let { Component.text(it) }?.let {
                    ItemBuilder.skull()
                        .owner(player)
                        .name(it)
                        .asGuiItem()
                }
                if (skullItem != null) {
                    playersGUI.addItem(skullItem)
                }
            }
        }
    }

    fun open(player: Player, refresh: Boolean = true) {
        if (refresh) refreshGUI()

        if (player.hasPermission("advancedservermanager.admin")) {
            playersGUI.open(player)
        } else {
            player.sendMessage(getConfigMessage("Permissions"))
        }
    }
}
