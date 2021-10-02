package me.pavelsgarklavs.advancedservermanager

import me.pavelsgarklavs.advancedservermanager.GUI.MainGUI
import me.pavelsgarklavs.advancedservermanager.commands.*
import net.milkbowl.vault.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getWorld
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class AdvancedServerManager : JavaPlugin() {
    private val mainGui: MainGUI = MainGUI(this)
    private val gamemodeCommand: GamemodeCommand = GamemodeCommand(this)
    private val flyCommand: FlyCommand = FlyCommand(this)
    private val godCommand: GodCommand = GodCommand(this)
    private val feedCommand: FeedCommand = FeedCommand(this)
    private val healCommand: HealCommand = HealCommand(this)
    private val weatherCommand: WeatherCommand = WeatherCommand(this)
    private val timeCommand: TimeCommand = TimeCommand(this)

    override fun onEnable() {
        println("\u001b[31mAdvanced Server Manager has started! \u001b[32;1mAuthor: \u001b[31mDev_Fox\u001b[0m")

        getCommand("admin")?.setExecutor(mainGui)
        getCommand("gm")?.setExecutor(gamemodeCommand)
        getCommand("fly")?.setExecutor(flyCommand)
        getCommand("god")?.setExecutor(godCommand)
        getCommand("feed")?.setExecutor(feedCommand)
        getCommand("heal")?.setExecutor(healCommand)
        getCommand("weather")?.setExecutor(weatherCommand)
        getCommand("time")?.setExecutor(timeCommand)

        config.options().copyDefaults()
        saveDefaultConfig()
    }

    override fun onDisable() {
        println("\u001b[31mAdvanced Server Manager has stopped!\u001b[0m")
    }
}