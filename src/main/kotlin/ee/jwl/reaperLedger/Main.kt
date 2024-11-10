package ee.jwl.reaperLedger

import ee.jwl.reaperLedger.commands.DeathCommand
import ee.jwl.reaperLedger.event.PlayerDeathListener
import ee.jwl.reaperLedger.event.PlayerInteractListener
import ee.jwl.reaperLedger.infrastructure.InMemoryLogRepository
import ee.jwl.reaperLedger.observer.playerDeath.InventoryBarrelMaker
import ee.jwl.reaperLedger.observer.playerDeath.LogSaver
import ee.jwl.reaperLedger.observer.playerInteract.BarrelLocker
import ee.jwl.reaperLedger.repository.LogRepository
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val logRepository: LogRepository = InMemoryLogRepository

    companion object {
        lateinit var instance: Main
    }

    private fun injectCommands() {
        CommandRegistrar.addCommand(DeathCommand(logRepository))
    }

    private fun injectPlayerDeathObservers() {
        EventRegistrar.addEvent(PlayerDeathListener)

        PlayerDeathListener.addObserver(LogSaver(logRepository))
        PlayerDeathListener.addObserver(InventoryBarrelMaker())
    }

    private fun injectInteractObservers() {
        EventRegistrar.addEvent(PlayerInteractListener)

        PlayerInteractListener.addObserver(BarrelLocker())
    }

    override fun onLoad() {
        injectCommands()
        injectPlayerDeathObservers()
        injectInteractObservers()
    }

    override fun onEnable() {
        instance = this

        EventRegistrar.register()
        CommandRegistrar.register()
        logger.info("DeathPoint plugin enabled")
    }

    override fun onDisable() {
        logger.info("DeathPoint plugin disabled")
    }
}
