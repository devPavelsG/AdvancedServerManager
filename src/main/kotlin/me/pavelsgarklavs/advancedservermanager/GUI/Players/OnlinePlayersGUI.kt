package me.pavelsgarklavs.advancedservermanager.GUI.Players

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.PaginatedGui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.listeners.AnvilGUIClickListener
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player


class OnlinePlayersGUI(plugin: AdvancedServerManager, private var sender: Player) : Utils(plugin) {
    lateinit var playersGUI: PaginatedGui

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
                    val selectedPlayer = Gui.gui()
                        .title(Component.text("Player: ${player.name}", NamedTextColor.GOLD, TextDecoration.BOLD))
                        .rows(6)
                        .create()
                    selectedPlayer.setDefaultClickAction { event -> event.isCancelled = true }

                    val searchItem = ItemBuilder
                        .from(Material.PAPER)
                        .name(Component.text("Search Player", NamedTextColor.AQUA, TextDecoration.BOLD))
                        .asGuiItem {
                            val searchGUI = SearchGUI(plugin)
                            searchGUI.createSearchGUI(sender)
                            plugin.server.pluginManager.registerEvents(AnvilGUIClickListener(plugin, sender), plugin)
                        }

                    selectedPlayer.setItem(1, searchItem)

//                    /* Creative Mode */
//                    val creativeItem = ItemBuilder
//                        .from(Material.GOLDEN_APPLE)
//                        .name(Component.text("Creative Mode", NamedTextColor.GOLD, TextDecoration.BOLD))
//                        .asGuiItem {
//                                if (player.gameMode !== GameMode.CREATIVE) {
//                                    player.gameMode = GameMode.CREATIVE
//                                    if (plugin.config.getBoolean("GUISendTitle")) {
//                                        player.sendTitle(
//                                            getGUIConfigMessage("CreativeTitle"),
//                                            getGUIConfigMessage("CreativeSubtitleOn"),
//                                            10, 60, 20
//                                        )
//                                    }
//                                } else {
//                                    player.gameMode = GameMode.SURVIVAL
//                                    if (plugin.config.getBoolean("GUISendTitle")) {
//                                        player.sendTitle(
//                                            getGUIConfigMessage("CreativeTitle"),
//                                            getGUIConfigMessage("CreativeSubtitleOff"),
//                                            10, 60, 20
//                                        )
//                                    }
//                                }
//                                onButtonSound(player)
//
//                            selectedPlayer.close(sender)
//                        }
//
//                    selectedPlayer.setItem(1, creativeItem)

                    selectedPlayer.open(sender)
                }
            if (skullItem != null) {
                playersGUI.addItem(skullItem)
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