package me.pavelsgarklavs.advancedservermanager.GUI.Players

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class SelectedPlayerGUI(plugin: AdvancedServerManager) : Utils(plugin) {
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
                    selectedPlayer.close(player)
                    onButtonSound(player)
                }
            selectedPlayer.setItem(49, closeButton)
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

        /* Survival Mode */
        val survivalItem = ItemBuilder
            .from(Material.BREAD)
            .name(Component.text("Survival Mode", NamedTextColor.AQUA, TextDecoration.BOLD))
            .asGuiItem {
                if (player.gameMode !== GameMode.SURVIVAL) {
                    player.gameMode = GameMode.SURVIVAL
                    if (plugin.config.getBoolean("GUISendTitle")) {
                        player.sendTitle(
                            getGUIConfigMessage("SurvivalTitle"),
                            getGUIConfigMessage("SurvivalSubtitleOn"),
                            10, 60, 20
                        )
                    }
                    onButtonSound(player)
                } else {
                    if (plugin.config.getBoolean("GUISendTitle")) {
                        player.sendTitle(
                            getGUIConfigMessage("SurvivalAlreadyOnTitle"),
                            getGUIConfigMessage("SurvivalSubtitleOn"),
                            10, 60, 20
                        )
                    }
                    errorButtonSound(player)
                }
                guiClose()
            }

        /* Spectator Mode */
        val spectatorItem = ItemBuilder
            .from(Material.ENDER_EYE)
            .name(Component.text("Spectator Mode", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
            .asGuiItem {
                if (player.gameMode !== GameMode.SPECTATOR) {
                    player.gameMode = GameMode.SPECTATOR
                    if (plugin.config.getBoolean("GUISendTitle")) {
                        player.sendTitle(
                            getGUIConfigMessage("SpectatorTitle"),
                            getGUIConfigMessage("SpectatorSubtitleOn"),
                            10, 60, 20
                        )
                    }
                } else {
                    player.gameMode = GameMode.SURVIVAL
                    onButtonSound(player)
                    if (plugin.config.getBoolean("GUISendTitle")) {
                        player.sendTitle(
                            getGUIConfigMessage("SpectatorTitle"),
                            getGUIConfigMessage("SpectatorSubtitleOff"),
                            10, 60, 20
                        )
                    }
                }
                onButtonSound(player)
                guiClose()
            }

        /* Fly */
        val flyItem = ItemBuilder
            .from(Material.ELYTRA)
            .name(Component.text("Turn ON/OFF Flying", NamedTextColor.GRAY, TextDecoration.BOLD))
            .asGuiItem {
                if (player.gameMode == GameMode.SURVIVAL) {
                    if (!player.isFlying && !player.allowFlight) {
                        player.allowFlight = true
                        player.isFlying = true
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("FlyTitle"),
                                getGUIConfigMessage("FlySubtitleOn"),
                                10, 60, 20
                            )
                        }
                    } else {
                        player.allowFlight = false
                        player.isFlying = false
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("FlyTitle"),
                                getGUIConfigMessage("FlySubtitleOff"),
                                10, 60, 20
                            )
                        }
                    }
                    onButtonSound(player)
                } else {
                    if (plugin.config.getBoolean("GUISendTitle")) {
                        player.sendTitle(
                            getGUIConfigMessage("FlyMustBeSurvivalTitle"),
                            getGUIConfigMessage("FlyMustBeSurvivalSubtitle"),
                            10, 60, 20
                        )
                    }
                    errorButtonSound(player)
                }
                guiClose()
            }

        /* God Mode */
        val godItem = ItemBuilder
            .from(Material.ENCHANTED_GOLDEN_APPLE)
            .name(Component.text("Turn ON/OFF God Mode", NamedTextColor.DARK_RED, TextDecoration.BOLD))
            .asGuiItem {
                if (player.gameMode == GameMode.SURVIVAL) {
                    if (player.isInvulnerable) {
                        player.isInvulnerable = false
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("GodTitle"),
                                getGUIConfigMessage("GodSubtitleOff"),
                                10, 60, 20
                            )
                        }
                    } else {
                        player.isInvulnerable = true
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("GodTitle"),
                                getGUIConfigMessage("GodSubtitleOn"),
                                10, 60, 20
                            )
                        }
                    }
                    onButtonSound(player)
                } else {
                    if (plugin.config.getBoolean("GUISendTitle")) {
                        player.sendTitle(
                            getGUIConfigMessage("GodMustBeSurvivalTitle"),
                            getGUIConfigMessage("GodMustBeSurvivalSubtitle"),
                            10, 60, 20
                        )
                    }
                    errorButtonSound(player)
                }
                guiClose()
            }

        /* Feed/Heal */
        val feedHealItem = ItemBuilder
            .from(Material.APPLE)
            .name(Component.text("Feed/Heal", NamedTextColor.GREEN, TextDecoration.BOLD))
            .asGuiItem {
                player.health = 20.0
                player.foodLevel = 20
                if (plugin.config.getBoolean("GUISendTitle")) {
                    player.sendTitle(
                        getGUIConfigMessage("FeedHealTitle"),
                        getGUIConfigMessage("FeedHealSubtitle"),
                        10, 60, 20
                    )
                }
                onButtonSound(player)
                guiClose()
            }

        /* Home */
        val homeItem = ItemBuilder
            .from(Material.ENDER_EYE)
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

                    sender.teleport(loc)

                } catch (e: NullPointerException) {
                    selectedPlayer.close(sender)
                    if (getConfigMessage("HomeDoesNotExistOthers").contains("%player_name%")) {
                        val message = getConfigMessage("HomeDoesNotExistOthers").replace("%player_name%", player.name)
                        sender.sendMessage(message)
                    } else {
                        val message = getConfigMessage("HomeDoesNotExistOthers")
                        sender.sendMessage(message)
                    }
                }
            }

        selectedPlayer.setItem(10, creativeItem)
        selectedPlayer.setItem(11, survivalItem)
        selectedPlayer.setItem(12, spectatorItem)
        selectedPlayer.setItem(14, flyItem)
        selectedPlayer.setItem(15, godItem)
        selectedPlayer.setItem(16, feedHealItem)
        selectedPlayer.setItem(28, homeItem)

        selectedPlayer.open(sender)
    }

}