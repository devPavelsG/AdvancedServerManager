package me.pavelsgarklavs.advancedservermanager.GUI.Players.PlayerGUI

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.PaginatedGui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.GUI.Players.SearchGUI.SearchOfflineGUI
import me.pavelsgarklavs.advancedservermanager.GUI.Players.SelectedPlayerGUI.SelectedOfflinePlayerGUI
import me.pavelsgarklavs.advancedservermanager.GUI.Players.SearchGUI.SearchOnlineGUI
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class OfflinePlayersGUI(plugin: AdvancedServerManager, private var sender: Player) : Utils(plugin) {
    private val searchOfflineGUI: SearchOfflineGUI = SearchOfflineGUI(plugin)
    private val selectedOfflinePlayerGUI: SelectedOfflinePlayerGUI = SelectedOfflinePlayerGUI(plugin)
    private lateinit var playersGUI: PaginatedGui

    private val searchItem = ItemBuilder
        .from(Material.COMPASS)
        .name(Component.text("Search Player", NamedTextColor.AQUA, TextDecoration.BOLD))
        .asGuiItem {
            searchOfflineGUI.createSearchGUI(sender, plugin)
        }

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
                        .asGuiItem {
                            selectedOfflinePlayerGUI.createSelectedPlayerGUI(sender, player)
                        }
                }
                if (skullItem != null) playersGUI.addItem(skullItem)
                playersGUI.setItem(49, searchItem)
            }
        }
    }

    fun open(player: Player, refresh: Boolean = true) {
        if (refresh) refreshGUI()

        if (player.hasPermission("advancedservermanager.admin")) {
            playersGUI.open(player)
        } else player.sendMessage(getConfigMessage("Permissions"))
    }
}
