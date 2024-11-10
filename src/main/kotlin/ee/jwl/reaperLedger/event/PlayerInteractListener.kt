package ee.jwl.reaperLedger.event

import ee.jwl.reaperLedger.observer.Observer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object PlayerInteractListener : Listener, Observable<PlayerInteractEvent> {
    private val observers = mutableListOf<Observer<PlayerInteractEvent>>()

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        notifyObservers(event)
    }

    override fun addObserver(observer: Observer<PlayerInteractEvent>) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }

        return;
    }

    override fun removeObserver(observer: Observer<PlayerInteractEvent>) {
        if (observers.contains(observer)) {
            observers.remove(observer)
        }

        throw IllegalArgumentException("Observer does not exist")
    }

    override fun notifyObservers(event: PlayerInteractEvent) {
        observers.forEach { it.update(event) }
    }

}