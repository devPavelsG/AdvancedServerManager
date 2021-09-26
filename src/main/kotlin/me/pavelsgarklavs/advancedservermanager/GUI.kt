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

class GUI : CommandExecutor {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender is Player) {
            val player: Player = sender

            fun onButtonSound() {
                return player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 20F, 0F)
            }

            fun errorButtonSound() {
                return player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 20F, 0F)
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
                    if (player.hasPermission("advancedservermanager.admin.creative")) {
                        if (player.gameMode !== GameMode.CREATIVE) {
                            player.gameMode = GameMode.CREATIVE
                            player.sendTitle(
                                "${ChatColor.GOLD}Creative Mode",
                                "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                10, 60, 20
                            )
                        } else {
                            player.gameMode = GameMode.SURVIVAL
                            player.sendTitle(
                                "${ChatColor.GOLD}Creative Mode",
                                "${ChatColor.RED}${ChatColor.BOLD}OFF",
                                10, 60, 20
                            )
                        }
                        onButtonSound()
                    } else {
                        errorButtonSound()
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                    }
                    gui.close(player)
                }

            /* Survival Mode */
            val survivalItem = ItemBuilder
                .from(Material.BREAD)
                .name(Component.text("Survival Mode", NamedTextColor.AQUA, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.admin.survival")) {
                        if (player.gameMode !== GameMode.SURVIVAL) {
                            player.gameMode = GameMode.SURVIVAL
                            player.sendTitle(
                                "${ChatColor.AQUA}Survival Mode",
                                "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                10, 60, 20
                            )
                            onButtonSound()
                        } else {
                            player.sendTitle(
                                "${ChatColor.DARK_RED}Survival Mode is already",
                                "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                10, 60, 20
                            )
                            errorButtonSound()
                        }
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                        errorButtonSound()
                    }
                    gui.close(player)
                }

            /* Spectator Mode */
            val spectatorItem = ItemBuilder
                .from(Material.ENDER_EYE)
                .name(Component.text("Spectator Mode", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.admin.spectator")) {
                        if (player.gameMode !== GameMode.SPECTATOR) {
                            player.gameMode = GameMode.SPECTATOR
                            player.sendTitle(
                                "${ChatColor.LIGHT_PURPLE}Spectator Mode",
                                "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                10, 60, 20
                            )
                        } else {
                            player.gameMode = GameMode.SURVIVAL
                            onButtonSound()
                            player.sendTitle(
                                "${ChatColor.LIGHT_PURPLE}Spectator Mode",
                                "${ChatColor.RED}${ChatColor.BOLD}OFF",
                                10, 60, 20
                            )
                        }
                        onButtonSound()
                    } else {
                        errorButtonSound()
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                    }
                    gui.close(player)
                }

            /* Fly */
            val flyItem = ItemBuilder
                .from(Material.ELYTRA)
                .name(Component.text("Turn ON/OFF Flying", NamedTextColor.GRAY, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.admin.fly")) {
                        if (player.gameMode == GameMode.SURVIVAL) {
                            if (!player.isFlying && !player.allowFlight) {
                                player.allowFlight = true
                                player.isFlying = true
                                player.sendTitle(
                                    "${ChatColor.GRAY}Flying turned",
                                    "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                    10, 60, 20
                                )
                            } else {
                                player.allowFlight = false
                                player.isFlying = false
                                player.sendTitle(
                                    "${ChatColor.GRAY}Flying turned",
                                    "${ChatColor.RED}${ChatColor.BOLD}OFF",
                                    10, 60, 20
                                )
                            }
                            onButtonSound()
                        } else {
                            player.sendTitle(
                                "${ChatColor.RED}Flying can't be turned ON",
                                "${ChatColor.DARK_RED}${ChatColor.BOLD}Enter survival mode to turn on",
                                10, 60, 20
                            )
                            errorButtonSound()
                        }
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                        errorButtonSound()
                    }
                    gui.close(player)
                }

            /* God Mode */
            val godItem = ItemBuilder
                .from(Material.ENCHANTED_GOLDEN_APPLE)
                .name(Component.text("Turn ON/OFF God Mode", NamedTextColor.DARK_RED, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.admin.god")) {
                        if (player.gameMode == GameMode.SURVIVAL) {
                            if (player.isInvulnerable) {
                                player.isInvulnerable = false
                                player.sendTitle(
                                    "${ChatColor.DARK_RED}God Mode turned",
                                    "${ChatColor.RED}${ChatColor.BOLD}OFF",
                                    10, 60, 20
                                )
                            } else {
                                player.isInvulnerable = true
                                player.sendTitle(
                                    "${ChatColor.DARK_RED}God Mode turned",
                                    "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                    10, 60, 20
                                )
                            }
                            onButtonSound()
                        } else {
                            player.sendTitle(
                                "${ChatColor.RED}God Mode can't be turned ON",
                                "${ChatColor.DARK_RED}${ChatColor.BOLD}Enter survival mode to turn on",
                                10, 60, 20
                            )
                            errorButtonSound()
                        }
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                        errorButtonSound()
                    }
                    gui.close(player)
                }

            /* Feed/Heal */
            val feedHealItem = ItemBuilder
                .from(Material.APPLE)
                .name(Component.text("Feed/Heal", NamedTextColor.GREEN, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedservermanager.admin.feedheal")) {
                        player.health = 20.0
                        player.foodLevel = 20
                        player.sendTitle(
                            "${ChatColor.GREEN}Server fed and healed you",
                            "",
                            10, 60, 20
                        )
                        onButtonSound()
                    } else {
                        errorButtonSound()
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
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
                player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
            }
        }
        return true
    }
}