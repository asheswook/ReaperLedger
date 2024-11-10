package ee.jwl.reaperLedger.observer

import org.bukkit.event.Event

interface Observer<T : Event> {
    fun update(event: T)
}