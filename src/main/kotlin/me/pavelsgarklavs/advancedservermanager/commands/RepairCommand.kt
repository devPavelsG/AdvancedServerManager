package me.pavelsgarklavs.advancedservermanager.commands

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.Damageable


class RepairCommand(plugin: AdvancedServerManager) : CommandExecutor, Utils(plugin) {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val setDurabilityToMax = 0

        if (args.isEmpty()) {
            ifPermissible(sender, "advancedservermanager.repair", {
                val player: Player = sender as Player
                val itemInMainHand = player.inventory.itemInMainHand

                if (itemInMainHand.itemMeta != null) {
                    val newItemMeta = itemInMainHand.itemMeta

                    (newItemMeta as Damageable).damage = setDurabilityToMax

                    itemInMainHand.itemMeta = newItemMeta
                    player.sendMessage(getConfigMessage("Repair"))
                }
            }, true)
            return true
        } else if (args.size == 1) {
            ifPermissible(sender, "advancedservermanager.repairall", {
                val player: Player = sender as Player
                val inventoryContent = player.inventory.contents

                for (item in inventoryContent) {
                    if (item != null) {
                        val newItemMeta = item.itemMeta
                        (newItemMeta as Damageable).damage = setDurabilityToMax

                        item.itemMeta = newItemMeta
                    }
                }
                player.sendMessage(getConfigMessage("RepairAll"))
            }, true)
            return  true
        } else sender.sendMessage(getConfigMessage("ErrorArguments"))
        return false
    }
}