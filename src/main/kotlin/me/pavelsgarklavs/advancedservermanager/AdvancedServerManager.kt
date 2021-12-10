package me.pavelsgarklavs.advancedservermanager

import me.pavelsgarklavs.advancedservermanager.GUI.MainGUI
import me.pavelsgarklavs.advancedservermanager.commands.*
import me.pavelsgarklavs.advancedservermanager.commands.Home.HomeCommand
import me.pavelsgarklavs.advancedservermanager.commands.Home.SetHomeCommand
import me.pavelsgarklavs.advancedservermanager.listeners.StaffChatListener
import org.bukkit.plugin.java.JavaPlugin

class AdvancedServerManager : JavaPlugin() {
    private val mainGui: MainGUI = MainGUI(this)
    private val gamemodeCommand: GamemodeCommand = GamemodeCommand(this)
    private val flyCommand: FlyCommand = FlyCommand(this)
    private val godCommand: GodCommand = GodCommand(this)
    private val feedCommand: FeedCommand = FeedCommand(this)
    private val healCommand: HealCommand = HealCommand(this)
    private val weatherCommand: WeatherCommand = WeatherCommand(this)
    private val timeCommand: TimeCommand = TimeCommand(this)
    private val setHomeCommand: SetHomeCommand = SetHomeCommand(this)
    private val homeCommand: HomeCommand = HomeCommand(this)
    private val teleportCommand: TeleportCommand = TeleportCommand(this)

    override fun onEnable() {
        println("\u001b[31mAdvanced Server Manager has started! \u001b[32;1mAuthor: \u001b[31mDev_Pavels\u001b[0m")

        server.pluginManager.registerEvents(StaffChatListener(this), this)

        getCommand("admin")?.setExecutor(mainGui)
        getCommand("gm")?.setExecutor(gamemodeCommand)
        getCommand("fly")?.setExecutor(flyCommand)
        getCommand("god")?.setExecutor(godCommand)
        getCommand("feed")?.setExecutor(feedCommand)
        getCommand("heal")?.setExecutor(healCommand)
        getCommand("weather")?.setExecutor(weatherCommand)
        getCommand("time")?.setExecutor(timeCommand)
        getCommand("sethome")?.setExecutor(setHomeCommand)
        getCommand("home")?.setExecutor(homeCommand)
        getCommand("tp")?.setExecutor(teleportCommand)

        config.options().copyDefaults()
        saveDefaultConfig()
    }

    override fun onDisable() {
        println("\u001b[31mAdvanced Server Manager has stopped!\u001b[0m")
    }
}