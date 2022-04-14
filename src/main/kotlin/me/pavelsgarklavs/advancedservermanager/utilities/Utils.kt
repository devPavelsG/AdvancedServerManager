package me.pavelsgarklavs.advancedservermanager.utilities

import dev.triumphteam.gui.guis.Gui
import me.pavelsgarklavs.advancedservermanager.AdvancedServerManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
abstract class Utils(val plugin: AdvancedServerManager) {

    fun getOverWorld(): World? {
        var overworld: World? = null
        for (worlds in Bukkit.getWorlds()) {
            if (worlds.name == "world") overworld = worlds
        }
        return overworld
    }

    fun getGUIConfigMessage(path: String): String? {
        return plugin.config.getString(path)
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
    }

    fun onButtonSound(player: Player) {
        if (plugin.config.getBoolean("SuccessfulSound")) player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 20F, 0F)
    }

    fun errorButtonSound(player: Player) {
        if (plugin.config.getBoolean("ErrorSound")) player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 20F, 0F)
    }

//    fun getOfflinePlayer(name: String): Optional<OfflinePlayer> {
//        for (player in Bukkit.getOfflinePlayers()) {
//            if (player.name.equals(name, ignoreCase = true)) return Optional.of(player)
//        }
//        return Optional.empty()
//    }

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
        return if (prefix?.isEmpty() == true) message.toString() else "$prefix $message"
    }

    fun guiClose(gui: Gui, player: Player) {
        if (plugin.config.getBoolean("GUIAutoClose")) gui.close(player)
    }

    fun ifPermissible(sender: CommandSender, permission: String, action: Runnable, mustBePlayer: Boolean = false) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(getConfigMessage("Permissions"))
        } else if (!mustBePlayer || sender is Player) {
                action.run()
        } else sender.sendMessage(getConfigMessage("MustBePlayer"))
    }
}

