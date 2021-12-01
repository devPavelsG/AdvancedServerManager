package me.pavelsgarklavs.advancedservermanager.GUI.Players

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class SearchGUI(plugin: AdvancedServerManager) : Utils(plugin) {

    fun createSearchGUI(player: Player) {
        val searchGui: Inventory =
            Bukkit.getServer().createInventory(null, InventoryType.ANVIL, "Enter player name:")

        val paperItem = ItemStack(Material.PAPER, 1)
        val im = paperItem.itemMeta
        im!!.setDisplayName("Search: ")
        paperItem.itemMeta = im

        searchGui.setItem(0, paperItem)

        player.openInventory(searchGui)
    }


}