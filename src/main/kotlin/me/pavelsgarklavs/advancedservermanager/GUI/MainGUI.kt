package me.pavelsgarklavs.advancedservermanager.GUI

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.GUI.Players.PlayerGUI.OfflinePlayersGUI
import me.pavelsgarklavs.advancedservermanager.GUI.Players.PlayerGUI.OnlinePlayersGUI
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import java.io.File

class MainGUI(plugin: AdvancedServerManager) : CommandExecutor, Utils(plugin) {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender is Player) {
            val player: Player = sender

            /* GUI */
            val gui: Gui = Gui.gui()
                .rows(6)
                .title(Component.text("Admin GUI", NamedTextColor.RED, TextDecoration.BOLD))
                .create()

            gui.setDefaultClickAction { event -> event.isCancelled = true }

            /* GUI Borders */
            val borderItem = ItemBuilder
                .from(Material.BLACK_STAINED_GLASS_PANE)
                .name(Component.text("X", NamedTextColor.RED, TextDecoration.BOLD))
                .asGuiItem()

            gui.filler.fillBorder(borderItem)

            /* GUI Close */
            if (!plugin.config.getBoolean("GUIAutoClose")) {
                val closeButton = ItemBuilder
                    .from(Material.BARRIER)
                    .name(Component.text("Close GUI", NamedTextColor.RED, TextDecoration.BOLD))
                    .asGuiItem {
                        gui.close(player)
                        onButtonSound(player)
                    }
                gui.setItem(49, closeButton)
            }

            /* Creative Mode */
            val creativeItem = ItemBuilder
                .from(Material.GOLDEN_APPLE)
                .name(Component.text("Creative Mode", NamedTextColor.GOLD, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.creative")) {
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
                    } else {
                        errorButtonSound(player)
                        player.sendMessage(getConfigMessage("Permissions"))
                    }
                    guiClose(gui, player)
                }

            /* Survival Mode */
            val survivalItem = ItemBuilder
                .from(Material.BREAD)
                .name(Component.text("Survival Mode", NamedTextColor.AQUA, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.survival")) {
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
                    } else {
                        player.sendMessage(getConfigMessage("Permissions"))
                        errorButtonSound(player)
                    }
                    guiClose(gui, player)
                }

            /* Spectator Mode */
            val spectatorItem = ItemBuilder
                .from(Material.ENDER_EYE)
                .name(Component.text("Spectator Mode", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.spectator")) {
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
                    } else {
                        errorButtonSound(player)
                        player.sendMessage(getConfigMessage("Permissions"))
                    }
                    guiClose(gui, player)
                }

            /* Fly */
            val flyItem = ItemBuilder
                .from(Material.ELYTRA)
                .name(Component.text("Turn ON/OFF Flying", NamedTextColor.GRAY, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.fly")) {
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
                    } else {
                        player.sendMessage(getConfigMessage("Permissions"))
                        errorButtonSound(player)
                    }
                    guiClose(gui, player)
                }

            /* God Mode */
            val godItem = ItemBuilder
                .from(Material.ENCHANTED_GOLDEN_APPLE)
                .name(Component.text("Turn ON/OFF God Mode", NamedTextColor.DARK_RED, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.god")) {
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
                    } else {
                        player.sendMessage(getConfigMessage("Permissions"))
                        errorButtonSound(player)
                    }
                    guiClose(gui, player)
                }

            /* Feed/Heal */
            val feedHealItem = ItemBuilder
                .from(Material.APPLE)
                .name(Component.text("Feed/Heal", NamedTextColor.GREEN, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.feedheal")) {
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
                    } else {
                        errorButtonSound(player)
                        player.sendMessage(getConfigMessage("Permissions"))
                    }
                    guiClose(gui, player)
                }
            /* Weather */
            val clearWeatherItem = ItemBuilder
                .from(Material.GLASS_BOTTLE)
                .name(Component.text("Clear Weather", NamedTextColor.GOLD, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.weather")) {
                        getOverWorld()?.clearWeatherDuration = ((0..599).random()) * 20
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("ClearWeatherTitle"),
                                getGUIConfigMessage("ClearWeatherSubtitle"),
                                10, 60, 20
                            )
                        }
                        onButtonSound(player)
                    } else {
                        errorButtonSound(player)
                        player.sendMessage(getConfigMessage("Permissions"))
                    }
                    guiClose(gui, player)
                }

            val rainWeatherItem = ItemBuilder
                .from(Material.POTION)
                .name(Component.text("Rainy Weather", NamedTextColor.BLUE, TextDecoration.BOLD))
                .flags(ItemFlag.HIDE_POTION_EFFECTS)
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.weather")) {
                        getOverWorld()?.setStorm(true)
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("RainWeatherTitle"),
                                getGUIConfigMessage("RainWeatherSubtitle"),
                                10, 60, 20
                            )
                        }
                        onButtonSound(player)
                    } else {
                        errorButtonSound(player)
                        player.sendMessage(getConfigMessage("Permissions"))
                    }
                    guiClose(gui, player)
                }

            /* Set Time */
            val setDayItem = ItemBuilder
                .from(Material.SUNFLOWER)
                .name(Component.text("Day", NamedTextColor.YELLOW, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.time")) {
                        getOverWorld()?.time = 1000
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("DayTitle"),
                                getGUIConfigMessage("DaySubtitle"),
                                10, 60, 20
                            )
                        }
                        onButtonSound(player)
                    } else {
                        errorButtonSound(player)
                        player.sendMessage(getConfigMessage("Permissions"))
                    }
                    guiClose(gui, player)
                }

            val setNightItem = ItemBuilder
                .from(Material.WITHER_ROSE)
                .name(Component.text("Night", NamedTextColor.DARK_GRAY, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.time")) {
                        getOverWorld()?.time = 18000
                        if (plugin.config.getBoolean("GUISendTitle")) {
                            player.sendTitle(
                                getGUIConfigMessage("NightTitle"),
                                getGUIConfigMessage("NightSubtitle"),
                                10, 60, 20
                            )
                        }
                        onButtonSound(player)
                    } else {
                        errorButtonSound(player)
                        player.sendMessage(getConfigMessage("Permissions"))
                    }
                    guiClose(gui, player)
                }

            /* Vanish */

            val vanishItem = ItemBuilder
                .from(Material.AMETHYST_SHARD)
                .name(Component.text("Vanish", NamedTextColor.DARK_GRAY, TextDecoration.BOLD))
                .asGuiItem {
                    ifPermissible(player, "advancedservermanager.vanish", {
                        try {
                            val playerUUID = player.uniqueId.toString()
                            val file = File(plugin.dataFolder, "players.yml")
                            if (!file.exists()) {
                                file.createNewFile()
                            }

                            val config = YamlConfiguration.loadConfiguration(file)
                            if (!config.contains(playerUUID)) {
                                config.set("$playerUUID.vanish", true)
                            }

                            val isVanish = config.getBoolean("$playerUUID.vanish")

                            if (isVanish) {
                                for (allPlayers in Bukkit.getOnlinePlayers()) {
                                    allPlayers.hidePlayer(plugin, player)
                                }
                                config.set("$playerUUID.vanish", false)
                                if (plugin.config.getBoolean("GUISendTitle")) {
                                    player.sendTitle(
                                        getGUIConfigMessage("VanishTitle"),
                                        getGUIConfigMessage("VanishOnSubtitle"),
                                        10, 60, 20
                                    )
                                }
                            } else {
                                for (allPlayers in Bukkit.getOnlinePlayers()) {
                                    allPlayers.showPlayer(plugin, player)
                                }
                                config.set("$playerUUID.vanish", true)
                                if (plugin.config.getBoolean("GUISendTitle")) {
                                    player.sendTitle(
                                        getGUIConfigMessage("VanishTitle"),
                                        getGUIConfigMessage("VanishOffSubtitle"),
                                        10, 60, 20
                                    )
                                }
                            }
                            onButtonSound(player)
                            config.save(file)
                            guiClose(gui, player)
                        } catch (e: Exception) {
                            errorButtonSound(player)
                            println(e.printStackTrace())
                        }
                    }, true)
                }

            /* Players GUI */
            val onlinePlayersGUIItem = ItemBuilder
                .from(Material.PLAYER_HEAD)
                .name(Component.text("Online Players", NamedTextColor.GOLD, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.players")) {
                        OnlinePlayersGUI(plugin, player).open(player)
                    }
                }
            val offlinePlayersGUIItem = ItemBuilder
                .from(Material.PLAYER_HEAD)
                .name(Component.text("Offline Players", NamedTextColor.GOLD, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.players")) {
                        OfflinePlayersGUI(plugin, player).open(player)
                    }
                }

            /* Items -> Commands */


            gui.setItem(10, creativeItem)
            gui.setItem(11, survivalItem)
            gui.setItem(12, spectatorItem)
            gui.setItem(13, vanishItem)
            gui.setItem(14, flyItem)
            gui.setItem(15, godItem)
            gui.setItem(16, feedHealItem)
            gui.setItem(28, clearWeatherItem)
            gui.setItem(29, rainWeatherItem)
            gui.setItem(37, setDayItem)
            gui.setItem(38, setNightItem)
            gui.setItem(34, onlinePlayersGUIItem)
            gui.setItem(43, offlinePlayersGUIItem)


            /* Specific Borders */
            gui.setItem(19, borderItem)
            gui.setItem(20, borderItem)
            gui.setItem(21, borderItem)
            gui.setItem(22, borderItem)
            gui.setItem(23, borderItem)
            gui.setItem(24, borderItem)
            gui.setItem(25, borderItem)
            gui.setItem(30, borderItem)
            gui.setItem(31, borderItem)
            gui.setItem(32, borderItem)
            gui.setItem(33, borderItem)
            gui.setItem(39, borderItem)
            gui.setItem(40, borderItem)
            gui.setItem(41, borderItem)
            gui.setItem(42, borderItem)

            if (player.hasPermission("advancedservermanager.admin")) {
                gui.open(player)
            } else {
                player.sendMessage(getConfigMessage("Permissions"))
            }
        }
        return true
    }
}