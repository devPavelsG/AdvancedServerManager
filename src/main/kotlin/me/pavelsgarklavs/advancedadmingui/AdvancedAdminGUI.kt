package me.pavelsgarklavs.advancedadmingui

import me.pavelsgarklavs.advancedadmingui.commands.GamemodeCommand
import org.bukkit.plugin.java.JavaPlugin

class AdvancedAdminGUI : JavaPlugin() {
    private val gui: GUI = GUI()
    private val gamemodeCommand: GamemodeCommand = GamemodeCommand(this)

    override fun onEnable() {
        println("\u001b[31mAdvanced Admin GUI has started! \u001b[32;1mAuthor: \u001b[31mDev_Fox\u001b[0m")

        getCommand("admin")?.setExecutor(gui)
        getCommand("gm")?.setExecutor(gamemodeCommand)
        getCommand("gm")?.tabCompleter = gamemodeCommand

        config.options().copyDefaults()
        saveDefaultConfig()
    }


    override fun onDisable() {
        println("\u001b[31mAdvanced Admin GUI has stopped!\u001b[0m")
    }
}