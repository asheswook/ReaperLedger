package ee.jwl.reaperLedger.event

import ee.jwl.reaperLedger.observer.Observer
import org.bukkit.event.Event

interface Observable<T : Event> {
    fun addObserver(observer: Observer<T>)
    fun removeObserver(observer: Observer<T>)
    fun notifyObservers(event: T)
}