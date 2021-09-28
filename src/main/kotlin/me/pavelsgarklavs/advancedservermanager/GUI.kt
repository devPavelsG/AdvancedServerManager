package me.pavelsgarklavs.advancedservermanager

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GUI(private val plugin: AdvancedServerManager) : CommandExecutor {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender is Player) {
            val player: Player = sender

            fun onButtonSound() {
                return player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 20F, 0F)
            }

            fun errorButtonSound() {
                return player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 20F, 0F)
            }

            fun getGUIConfigMessage(path: String): String? {
                return plugin.config.getString(path)
                    ?.let { ChatColor.translateAlternateColorCodes('&', it) }
            }

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

            /* Creative Mode */
            val creativeItem = ItemBuilder
                .from(Material.GOLDEN_APPLE)
                .name(Component.text("Creative Mode", NamedTextColor.GOLD, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.creative")) {
                        if (player.gameMode !== GameMode.CREATIVE) {
                            player.gameMode = GameMode.CREATIVE
                            player.sendTitle(
                                getGUIConfigMessage("CreativeTitle"),
                                getGUIConfigMessage("CreativeSubtitleOn"),
                                10, 60, 20
                            )
                        } else {
                            player.gameMode = GameMode.SURVIVAL
                            player.sendTitle(
                                getGUIConfigMessage("CreativeTitle"),
                                getGUIConfigMessage("CreativeSubtitleOff"),
                                10, 60, 20
                            )
                        }
                        onButtonSound()
                    } else {
                        errorButtonSound()
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                    gui.close(player)
                }

            /* Survival Mode */
            val survivalItem = ItemBuilder
                .from(Material.BREAD)
                .name(Component.text("Survival Mode", NamedTextColor.AQUA, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.survival")) {
                        if (player.gameMode !== GameMode.SURVIVAL) {
                            player.gameMode = GameMode.SURVIVAL
                            player.sendTitle(
                                getGUIConfigMessage("SurvivalTitle"),
                                getGUIConfigMessage("SurvivalSubtitleOn"),
                                10, 60, 20
                            )
                            onButtonSound()
                        } else {
                            player.sendTitle(
                                getGUIConfigMessage("SurvivalAlreadyOnTitle"),
                                getGUIConfigMessage("SurvivalSubtitleOn"),
                                10, 60, 20
                            )
                            errorButtonSound()
                        }
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                        errorButtonSound()
                    }
                    gui.close(player)
                }

            /* Spectator Mode */
            val spectatorItem = ItemBuilder
                .from(Material.ENDER_EYE)
                .name(Component.text("Spectator Mode", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.spectator")) {
                        if (player.gameMode !== GameMode.SPECTATOR) {
                            player.gameMode = GameMode.SPECTATOR
                            player.sendTitle(
                                getGUIConfigMessage("SpectatorTitle"),
                                getGUIConfigMessage("SpectatorSubtitleOn"),
                                10, 60, 20
                            )
                        } else {
                            player.gameMode = GameMode.SURVIVAL
                            onButtonSound()
                            player.sendTitle(
                                getGUIConfigMessage("SpectatorTitle"),
                                getGUIConfigMessage("SpectatorSubtitleOff"),
                                10, 60, 20
                            )
                        }
                        onButtonSound()
                    } else {
                        errorButtonSound()
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                    gui.close(player)
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
                                player.sendTitle(
                                    getGUIConfigMessage("FlyTitle"),
                                    getGUIConfigMessage("FlySubtitleOn"),
                                    10, 60, 20
                                )
                            } else {
                                player.allowFlight = false
                                player.isFlying = false
                                player.sendTitle(
                                    getGUIConfigMessage("FlyTitle"),
                                    getGUIConfigMessage("FlySubtitleOff"),
                                    10, 60, 20
                                )
                            }
                            onButtonSound()
                        } else {
                            player.sendTitle(
                                getGUIConfigMessage("FlyMustBeSurvivalTitle"),
                                getGUIConfigMessage("FlyMustBeSurvivalSubtitle"),
                                10, 60, 20
                            )
                            errorButtonSound()
                        }
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                        errorButtonSound()
                    }
                    gui.close(player)
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
                                player.sendTitle(
                                    getGUIConfigMessage("GodTitle"),
                                    getGUIConfigMessage("GodSubtitleOff"),
                                    10, 60, 20
                                )
                            } else {
                                player.isInvulnerable = true
                                player.sendTitle(
                                    getGUIConfigMessage("GodTitle"),
                                    getGUIConfigMessage("GodSubtitleOn"),
                                    10, 60, 20
                                )
                            }
                            onButtonSound()
                        } else {
                            player.sendTitle(
                                getGUIConfigMessage("GodMustBeSurvivalTitle"),
                                getGUIConfigMessage("GodMustBeSurvivalSubtitle"),
                                10, 60, 20
                            )
                            errorButtonSound()
                        }
                    } else {
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                        errorButtonSound()
                    }
                    gui.close(player)
                }

            /* Feed/Heal */
            val feedHealItem = ItemBuilder
                .from(Material.APPLE)
                .name(Component.text("Feed/Heal", NamedTextColor.GREEN, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.feedheal")) {
                        player.health = 20.0
                        player.foodLevel = 20
                        player.sendTitle(
                            getGUIConfigMessage("FeedHealTitle"),
                            getGUIConfigMessage("FeedHealSubtitle"),
                            10, 60, 20
                        )
                        onButtonSound()
                    } else {
                        errorButtonSound()
                        player.sendMessage(plugin.getConfigMessage("Permissions"))
                    }
                    gui.close(player)
                }

            /* Items -> Commands */
            gui.setItem(10, creativeItem)
            gui.setItem(11, survivalItem)
            gui.setItem(12, spectatorItem)
            gui.setItem(14, flyItem)
            gui.setItem(15, godItem)
            gui.setItem(16, feedHealItem)


            /* Specific Borders */
            gui.setItem(13, borderItem)
            gui.setItem(19, borderItem)
            gui.setItem(20, borderItem)
            gui.setItem(21, borderItem)
            gui.setItem(22, borderItem)
            gui.setItem(23, borderItem)
            gui.setItem(24, borderItem)
            gui.setItem(25, borderItem)

            if (player.hasPermission("advancedservermanager.admin")) {
                gui.open(player)
            } else {
                player.sendMessage(plugin.getConfigMessage("Permissions"))
            }
        }
        return true
    }
}