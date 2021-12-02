package me.pavelsgarklavs.advancedservermanager.GUI.Players

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player

class SelectedPlayerGUI(plugin: AdvancedServerManager): Utils(plugin) {
    fun createSelectedPlayerGUI(sender: Player, player: Player) {
        val selectedPlayer = Gui.gui()
            .title(Component.text("Player: ${player.name}", NamedTextColor.GOLD, TextDecoration.BOLD))
            .rows(6)
            .create()
        selectedPlayer.setDefaultClickAction { event -> event.isCancelled = true }

        fun guiClose() {
            if (plugin.config.getBoolean("GUIAutoClose")) {
                selectedPlayer.close(player)
            }
        }

        /* Creative Mode */
        val creativeItem = ItemBuilder
            .from(Material.GOLDEN_APPLE)
            .name(Component.text("Creative Mode", NamedTextColor.GOLD, TextDecoration.BOLD))
            .asGuiItem {
                    if (player.gameMode !== GameMode.CREATIVE) {
                        player.gameMode = GameMode.CREATIVE
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("CreativeTitle"),
                                getGUIConfigMessage("CreativeSubtitleOn"),
                                10, 60, 20
                            )
                        }
                    } else {
                        player.gameMode = GameMode.SURVIVAL
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("CreativeTitle"),
                                getGUIConfigMessage("CreativeSubtitleOff"),
                                10, 60, 20
                            )
                        }
                    }
                    onButtonSound(player)
                guiClose()
            }

        selectedPlayer.setItem(10, creativeItem)

        selectedPlayer.open(sender)
    }

}