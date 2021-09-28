package me.pavelsgarklavs.advancedservermanager

import me.pavelsgarklavs.advancedservermanager.commands.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier

class AdvancedServerManager : JavaPlugin() {
    private val gui: GUI = GUI(this)
    private val gamemodeCommand: GamemodeCommand = GamemodeCommand(this)
    private val flyCommand: FlyCommand = FlyCommand(this)
    private val godCommand: GodCommand = GodCommand(this)
    private val feedCommand: FeedCommand = FeedCommand(this)
    private val healCommand: HealCommand = HealCommand(this)

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
        val prefix = config.getString("Prefix")
            ?.let { ChatColor.translateAlternateColorCodes('&', it) }
        val message = config.getString(path)
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

    override fun onEnable() {
        println("\u001b[31mAdvanced Server Manager has started! \u001b[32;1mAuthor: \u001b[31mDev_Fox\u001b[0m")

        getCommand("admin")?.setExecutor(gui)
        getCommand("gm")?.setExecutor(gamemodeCommand)
        getCommand("gm")?.tabCompleter = gamemodeCommand
        getCommand("fly")?.setExecutor(flyCommand)
        getCommand("god")?.setExecutor(godCommand)
        getCommand("feed")?.setExecutor(feedCommand)
        getCommand("heal")?.setExecutor(healCommand)

        config.options().copyDefaults()
        saveDefaultConfig()
    }

    override fun onDisable() {
        println("\u001b[31mAdvanced Server Manager has stopped!\u001b[0m")
    }
}