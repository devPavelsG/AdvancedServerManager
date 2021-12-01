package me.pavelsgarklavs.advancedservermanager.Listeners

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

class AnvilGUIClickListener(private val plugin: AdvancedServerManager, private val player: Player) : Listener {

    @EventHandler
    fun clickEvent(event: InventoryClickEvent) {
        event.isCancelled = true

        if (event.slotType == InventoryType.SlotType.RESULT) {
            println("Test")
        }
    }
}