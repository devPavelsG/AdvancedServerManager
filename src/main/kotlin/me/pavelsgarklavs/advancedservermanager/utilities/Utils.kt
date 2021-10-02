package me.pavelsgarklavs.advancedservermanager.utilities

import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import net.milkbowl.vault.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

abstract class Utils(val plugin: AdvancedServerManager) {

    val world = Bukkit.getWorld("world")
    val chat = Bukkit.getServicesManager().load(Chat::class.java)

    fun getGUIConfigMessage(path: String): String? {
        return plugin.config.getString(path)
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
    }
    fun onButtonSound(player: Player) {
        return player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 20F, 0F)
    }
    fun errorButtonSound(player: Player) {
        return player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 20F, 0F)
    }
    fun getOfflinePlayer(name: String): Optional<OfflinePlayer> {
        for (player in Bukkit.getOfflinePlayers()) {
            if (player.name.equals(name, ignoreCase = true)) return Optional.of(player)
        }
        return Optional.empty()
    }
    fun getOnlinePlayer(name: String): Optional<Player> {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.name.equals(name, ignoreCase = true)) return Optional.of(player)
        }
        return Optional.empty()
    }
    fun getConfigMessage(path: String): String {
        val prefix = plugin.config.getString("Prefix")
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        val message = plugin.config.getString(path)
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        return if(prefix?.isEmpty() == true) message.toString() else "$prefix $message"
    }
    fun ifPermissible(sender: CommandSender, permission: String, action: Runnable, mustBePlayer: Boolean = false) {
        if(!sender.hasPermission(permission)) {
            sender.sendMessage(getConfigMessage("Permissions"))
        } else {
            if(!mustBePlayer || sender is Player) {
                action.run()
            } else {
                sender.sendMessage(getConfigMessage("MustBePlayer"))
            }
        }
    }
}