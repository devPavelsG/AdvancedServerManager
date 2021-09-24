package me.pavelsgarklavs.advancedadmingui

import me.pavelsgarklavs.advancedadmingui.commands.AdminCommand
import org.bukkit.plugin.java.JavaPlugin

class AdvancedAdminGUI : JavaPlugin() {

    private val adminCommand: AdminCommand = AdminCommand()

    override fun onEnable() {
        println("\u001b[31mAdvanced Admin GUI has started! \u001b[32;1mAuthor: \u001b[31mDev_Fox\u001b[0m")

        getCommand("admin")?.setExecutor(adminCommand)
    }

    override fun onDisable() {
        println("\u001b[31mAdvanced Admin GUI has stopped!\u001b[0m")
    }
}