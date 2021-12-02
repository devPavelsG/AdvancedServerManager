package me.pavelsgarklavs.advancedservermanager.GUI.Players

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class SearchGUI(plugin: AdvancedServerManager) {
    private val selectedPlayerGUI: SelectedPlayerGUI = SelectedPlayerGUI(plugin)
    var exists = false
    var existsPlayerObject: Player? = null

    fun createSearchGUI(sender: Player, plugin: AdvancedServerManager) {
        AnvilGUI.Builder()
            .onClose {
                if (exists && existsPlayerObject != null) {
                    selectedPlayerGUI.createSelectedPlayerGUI(sender, existsPlayerObject!!)
                }
            }
            .onComplete { _: Player, text: String ->
                for (username in Bukkit.getOnlinePlayers()) {
                    if (text.equals("Name: ${username.name}", ignoreCase = true)) {
                        exists = true
                        existsPlayerObject = username
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