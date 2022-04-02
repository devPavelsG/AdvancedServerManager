package me.pavelsgarklavs.advancedservermanager.GUI.Players.SelectedPlayerGUI

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.*
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.sql.Date

class SelectedOfflinePlayerGUI(plugin: AdvancedServerManager) : Utils(plugin) {
    fun createSelectedPlayerGUI(sender: Player, player: OfflinePlayer) {
        val selectedPlayer = Gui.gui()
            .title(Component.text("Player: ${player.name}", NamedTextColor.GOLD, TextDecoration.BOLD))
            .rows(6)
            .create()
        selectedPlayer.setDefaultClickAction { event -> event.isCancelled = true }

        fun guiClose() {
            if (plugin.config.getBoolean("GUIAutoClose")) {
                selectedPlayer.close(sender)
            }
        }
        /* GUI Borders */
        val borderItem = ItemBuilder
            .from(Material.BLACK_STAINED_GLASS_PANE)
            .name(Component.text("X", NamedTextColor.RED, TextDecoration.BOLD))
            .asGuiItem()

        selectedPlayer.setItem(13, borderItem)
        selectedPlayer.setItem(19, borderItem)
        selectedPlayer.setItem(20, borderItem)
        selectedPlayer.setItem(21, borderItem)
        selectedPlayer.setItem(22, borderItem)
        selectedPlayer.setItem(23, borderItem)
        selectedPlayer.setItem(24, borderItem)
        selectedPlayer.setItem(25, borderItem)
        selectedPlayer.setItem(30, borderItem)
        selectedPlayer.setItem(31, borderItem)
        selectedPlayer.setItem(32, borderItem)
        selectedPlayer.setItem(33, borderItem)
        selectedPlayer.setItem(39, borderItem)
        selectedPlayer.setItem(40, borderItem)
        selectedPlayer.setItem(41, borderItem)
        selectedPlayer.setItem(42, borderItem)

        selectedPlayer.filler.fillBorder(borderItem)

        /* GUI Close */
        if (!plugin.config.getBoolean("GUIAutoClose")) {
            val closeButton = ItemBuilder
                .from(Material.BARRIER)
                .name(Component.text("Close GUI", NamedTextColor.RED, TextDecoration.BOLD))
                .asGuiItem {
                    selectedPlayer.close(sender)
                    onButtonSound(sender)
                }
            selectedPlayer.setItem(49, closeButton)
        }

        /* Ban Player */
        val banItem = ItemBuilder
            .from(Material.OBSIDIAN)
            .name(Component.text("Ban", NamedTextColor.RED, TextDecoration.BOLD))
            .asGuiItem {
                val banList = Bukkit.getBanList(BanList.Type.NAME)
                val playerName: String = player.name.toString()
                banList.addBan(playerName, null, null, null)
                guiClose()
            }

        /* Unban Player */
        val unbanItem = ItemBuilder
            .from(Material.GLOWSTONE)
            .name(Component.text("Unban", NamedTextColor.GREEN, TextDecoration.BOLD))
            .asGuiItem {
                val banList = Bukkit.getBanList(BanList.Type.NAME)
                val playerName: String = player.name.toString()
                banList.pardon(playerName)
                guiClose()
            }

        /* Home */
        val homeItem = ItemBuilder
            .from(Material.BLAZE_POWDER)
            .name(Component.text("Home", NamedTextColor.DARK_GREEN, TextDecoration.BOLD))
            .asGuiItem {
                try {
                    val playerUUID = player.uniqueId.toString()
                    val file = File(plugin.dataFolder, "homes.yml")
                    val homes = YamlConfiguration.loadConfiguration(file)

                    val x = homes.get("$playerUUID.x")
                    val y = homes.get("$playerUUID.y")
                    val z = homes.get("$playerUUID.z")
                    val pitch = homes.get("$playerUUID.pitch")
                    val yaw = homes.get("$playerUUID.yaw")
                    val playerWorld = homes.get("$playerUUID.world")

                    val world = Bukkit.getServer().getWorld(playerWorld.toString())
                    val loc = Location(
                        world, (x as Int).toDouble(), (y as Int).toDouble(), (z as Int).toDouble(),
                        (yaw as Double).toFloat(), (pitch as Double).toFloat()
                    )
                    if (plugin.config.getBoolean("GUISendTitle")) {
                        sender.sendTitle(
                            getGUIConfigMessage("HomeTitle"),
                            getGUIConfigMessage("HomeSubtitle"),
                            10, 60, 20
                        )
                    }
                    onButtonSound(sender)
                    sender.teleport(loc)

                } catch (e: NullPointerException) {
                    selectedPlayer.close(sender)
                    if (getConfigMessage("HomeDoesNotExistOthers").contains("%player_name%")) {
                        val message = player.name?.let { it1 ->
                            getConfigMessage("HomeDoesNotExistOthers").replace("%player_name%",
                                it1
                            )
                        }
                        sender.sendMessage(message)
                    } else {
                        val message = getConfigMessage("HomeDoesNotExistOthers")
                        sender.sendMessage(message)
                    }
                    errorButtonSound(sender)
                }
                guiClose()
            }

        selectedPlayer.setItem(28, homeItem)
        selectedPlayer.setItem(29, banItem)
        selectedPlayer.setItem(38, unbanItem)

        selectedPlayer.open(sender)
    }

}