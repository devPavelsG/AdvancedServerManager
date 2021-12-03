package me.pavelsgarklavs.advancedservermanager.GUI.Players

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import me.pavelsgarklavs.advancedservermanager.utilities.Utils
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class SearchGUI(plugin: AdvancedServerManager) : Utils(plugin) {
    private val selectedPlayerGUI: SelectedPlayerGUI = SelectedPlayerGUI(plugin)
    var exists = false
    var existsPlayerObject: Player? = null

    fun createSearchGUI(sender: Player, plugin: AdvancedServerManager) {
        AnvilGUI.Builder()
            .onClose {
                if (exists && existsPlayerObject != null) {
                    selectedPlayerGUI.createSelectedPlayerGUI(sender, existsPlayerObject!!)
                } else {
                    val message = getConfigMessage("OfflineOrDoesNotExist")
                    sender.sendMessage(message)
                }
            }
            .onComplete { _: Player, text: String ->
                for (player in Bukkit.getOnlinePlayers()) {
                    if (text.equals("Name: ${player.name}", ignoreCase = true)) {
                        exists = true
                        existsPlayerObject = player
                    }
                }
                if (exists) {
                    return@onComplete AnvilGUI.Response.close()
                } else {
                    return@onComplete AnvilGUI.Response.close()
                }
            }
            .preventClose()
            .text("Name: ")
            .itemLeft(ItemStack(Material.PAPER))
            .title("Search for a player")
            .plugin(plugin)
            .open(sender)
    }
}