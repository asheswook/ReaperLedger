package ee.jwl.reaperLedger.event

import ee.jwl.reaperLedger.Main
import ee.jwl.reaperLedger.observer.Observer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener : Listener, Observable<PlayerDeathEvent> {
    private val observers: MutableList<Observer<PlayerDeathEvent>> = mutableListOf()

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        Main.instance.logger.info("Player death event at ${event.entity.location}")

        notifyObservers(event)
    }

    override fun addObserver(observer: Observer<PlayerDeathEvent>) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }

        return; // Ignore if observer already exists
    }

    override fun removeObserver(observer: Observer<PlayerDeathEvent>) {
        if (observers.contains(observer)) {
            observers.remove(observer)
        }

        throw IllegalArgumentException("Observer does not exist")
    }

    override fun notifyObservers(event: PlayerDeathEvent) {
        observers.forEach { it.update(event) }
    }
}