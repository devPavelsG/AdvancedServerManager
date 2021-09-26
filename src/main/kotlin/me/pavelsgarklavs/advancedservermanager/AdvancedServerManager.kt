package me.pavelsgarklavs.advancedservermanager

import me.pavelsgarklavs.advancedservermanager.commands.FlyCommand
import me.pavelsgarklavs.advancedservermanager.commands.GamemodeCommand
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class AdvancedServerManager : JavaPlugin() {
    private val gui: GUI = GUI()
    private val gamemodeCommand: GamemodeCommand = GamemodeCommand(this)
    private val flyCommand: FlyCommand = FlyCommand(this)

    fun checkOfflinePlayer(name: String): OfflinePlayer? {
        for (player in Bukkit.getOfflinePlayers()) {
            if (player.name == name) return player
        }
        return null
    }
    fun getSingleOnlinePlayer(): Player? {
        var singlePlayer: Player? = null
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            singlePlayer = onlinePlayer.player
        }
        return singlePlayer
    }
    fun getConfigMessage(path: String): String {
        val prefix = config.getString("Prefix")
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        val message = config.getString(path)
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        return prefix + message
    }

    override fun onEnable() {
        println("\u001b[31mAdvanced Server Manager has started! \u001b[32;1mAuthor: \u001b[31mDev_Fox\u001b[0m")

        getCommand("admin")?.setExecutor(gui)
        getCommand("gm")?.setExecutor(gamemodeCommand)
        getCommand("gm")?.tabCompleter = gamemodeCommand
        getCommand("fly")?.setExecutor(flyCommand)

        config.options().copyDefaults()
        saveDefaultConfig()
    }

    override fun onDisable() {
        println("\u001b[31mAdvanced Server Manager has stopped!\u001b[0m")
    }
}