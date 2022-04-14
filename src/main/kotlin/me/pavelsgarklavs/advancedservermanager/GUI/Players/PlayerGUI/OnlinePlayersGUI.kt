package me.pavelsgarklavs.advancedservermanager.GUI.Players.PlayerGUI

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.PaginatedGui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.GUI.Players.SelectedPlayerGUI.SelectedOnlinePlayerGUI
import me.pavelsgarklavs.advancedservermanager.GUI.Players.SearchGUI.SearchOnlineGUI
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player


class OnlinePlayersGUI(plugin: AdvancedServerManager, private var sender: Player) : Utils(plugin) {
    private val searchOnlineGUI: SearchOnlineGUI = SearchOnlineGUI(plugin)
    private val selectedOnlinePlayerGUI: SelectedOnlinePlayerGUI = SelectedOnlinePlayerGUI(plugin)
    lateinit var playersGUI: PaginatedGui

    private val searchItem = ItemBuilder
        .from(Material.COMPASS)
        .name(Component.text("Search Player", NamedTextColor.AQUA, TextDecoration.BOLD))
        .asGuiItem {
            searchOnlineGUI.createSearchGUI(sender, plugin)
        }

    init {
        createGUI()
    }

    private fun createGUI() {
        playersGUI = Gui.paginated()
            .title(Component.text("Online Players", NamedTextColor.GOLD, TextDecoration.BOLD))
            .rows(6)
            .pageSize(45)
            .create()
        playersGUI.setDefaultClickAction { event -> event.isCancelled = true }
    }

    private fun refreshGUI() {
        for (player in Bukkit.getOnlinePlayers()) {
            val skullItem = ItemBuilder.skull()
                .owner(player)
                .name(Component.text(player.name))
                .asGuiItem {
                    selectedOnlinePlayerGUI.createSelectedPlayerGUI(sender, player)
                }
            playersGUI.addItem(skullItem)
            playersGUI.setItem(49, searchItem)
        }
    }

    fun open(player: Player, refresh: Boolean = true) {
        if (refresh) refreshGUI()

        if (player.hasPermission("advancedservermanager.admin")) {
            playersGUI.open(player)
        } else player.sendMessage(getConfigMessage("Permissions"))
    }
}