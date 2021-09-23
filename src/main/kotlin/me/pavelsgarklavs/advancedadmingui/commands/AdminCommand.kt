package me.pavelsgarklavs.advancedadmingui.commands

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class AdminCommand: CommandExecutor {



    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender is Player) {
            val player: Player = sender

            /* GUI */
            val gui: Gui = Gui.gui()
                .rows(6)
                .title(Component.text("Admin GUI", NamedTextColor.RED, TextDecoration.BOLD))
                .create()

            gui.setDefaultClickAction { event -> event.isCancelled = true}

            /* GUI Borders */
            gui.filler.fillBorder(ItemBuilder
                .from(Material.BLACK_STAINED_GLASS_PANE)
                .name(Component.text("X", NamedTextColor.RED, TextDecoration.BOLD))
                .asGuiItem())

            val borderItem = ItemBuilder
                .from(Material.BLACK_STAINED_GLASS_PANE)
                .name(Component.text("X", NamedTextColor.RED, TextDecoration.BOLD))
                .asGuiItem()

            /* Creative Mode */
            val creativeItem = ItemBuilder
                .from(Material.GOLDEN_APPLE)
                .name(Component.text("Creative Mode", NamedTextColor.GOLD, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedadmingui.admin.creative")) {
                        if (player.gameMode !== GameMode.CREATIVE) {
                            player.gameMode = GameMode.CREATIVE
                            gui.close(player)
                            player.sendTitle(
                                "${ChatColor.GOLD}Creative Mode",
                                "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                10, 70, 20
                            )
                        } else {
                            player.gameMode = GameMode.SURVIVAL
                            gui.close(player)
                            player.sendTitle(
                                "${ChatColor.GOLD}Creative Mode",
                                "${ChatColor.RED}${ChatColor.BOLD}OFF",
                                10, 70, 20
                            )
                        }
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                    }
            }

            /* Survival Mode */
            val survivalItem = ItemBuilder
                .from(Material.BREAD)
                .name(Component.text("Survival Mode", NamedTextColor.AQUA, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedadmingui.admin.survival")) {
                        player.gameMode = GameMode.SURVIVAL
                        gui.close(player)
                        player.sendTitle(
                            "${ChatColor.AQUA}Survival Mode",
                            "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                            10, 70, 20
                        )
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                    }
                }

            /* Spectator Mode */
            val spectatorItem = ItemBuilder
                .from(Material.ENDER_EYE)
                .name(Component.text("Spectator Mode", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedadmingui.admin.spectator")) {
                        if (player.gameMode !== GameMode.SPECTATOR) {
                            player.gameMode = GameMode.SPECTATOR
                            gui.close(player)
                            player.sendTitle(
                                "${ChatColor.LIGHT_PURPLE}Spectator Mode",
                                "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                10, 70, 20
                            )
                        } else {
                            player.gameMode = GameMode.SURVIVAL
                            gui.close(player)
                            player.sendTitle(
                                "${ChatColor.LIGHT_PURPLE}Spectator Mode",
                                "${ChatColor.RED}${ChatColor.BOLD}OFF",
                                10, 70, 20
                            )
                        }
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                    }
                }

            /* Fly */
            val flyItem = ItemBuilder
                .from(Material.ELYTRA)
                .name(Component.text("Turn ON/OFF Flying", NamedTextColor.GRAY, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedadmingui.admin.fly")) {
                        if (player.gameMode == GameMode.SURVIVAL) {
                            if (!player.isFlying && !player.allowFlight) {
                                player.allowFlight = true
                                player.isFlying = true
                                player.sendTitle(
                                    "${ChatColor.GRAY}Flying turned",
                                    "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                    10, 70, 20
                                )
                            } else {
                                player.allowFlight = false
                                player.isFlying = false
                                player.sendTitle(
                                    "${ChatColor.GRAY}Flying turned",
                                    "${ChatColor.RED}${ChatColor.BOLD}OFF",
                                    10, 70, 20
                                )
                            }
                        } else {
                            player.sendTitle(
                                "${ChatColor.RED}Switch to Survival Mode",
                                "${ChatColor.RED}${ChatColor.BOLD}to turn flying on",
                                10, 70, 20
                            )
                        }
                        gui.close(player)
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                    }
                }

            /* God Mode */
            val godItem = ItemBuilder
                .from(Material.ENCHANTED_GOLDEN_APPLE)
                .name(Component.text("Turn ON/OFF God Mode", NamedTextColor.DARK_RED, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedadmingui.admin.god")) {
                        if (player.gameMode == GameMode.SURVIVAL) {
                            if (player.isInvulnerable) {
                                player.isInvulnerable = false
                                player.sendTitle(
                                    "${ChatColor.DARK_RED}God Mode turned",
                                    "${ChatColor.RED}${ChatColor.BOLD}OFF",
                                    10, 70, 20
                                )
                            } else {
                                player.isInvulnerable = true
                                player.sendTitle(
                                    "${ChatColor.DARK_RED}God Mode turned",
                                    "${ChatColor.GREEN}${ChatColor.BOLD}ON",
                                    10, 70, 20
                                )
                            }
                        } else {
                            player.sendTitle(
                                "${ChatColor.RED}God Mode can't be turned ON",
                                "${ChatColor.DARK_RED}${ChatColor.BOLD}Enter survival mode to turn on",
                                10, 70, 20
                            )
                        }

                        gui.close(player)
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                    }
                }

            /* Feed/Heal */
            val feedHealItem = ItemBuilder
                .from(Material.APPLE)
                .name(Component.text("Feed/Heal", NamedTextColor.GREEN, TextDecoration.BOLD))
                .asGuiItem {
                    if (player.hasPermission("advancedadmingui.admin.feedheal")) {
                        player.health = 20.0
                        player.foodLevel = 20
                        gui.close(player)
                        player.sendTitle(
                            "${ChatColor.GREEN}Server fed and healed you",
                            "",
                            10, 70, 20
                        )
                    } else {
                        player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
                    }
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

            if (player.hasPermission("advancedadmingui.admin")) {
                gui.open(player)
            } else {
                player.sendMessage("${ChatColor.DARK_RED}You do not have permissions to run this command!")
            }
        }
        return true
    }
}