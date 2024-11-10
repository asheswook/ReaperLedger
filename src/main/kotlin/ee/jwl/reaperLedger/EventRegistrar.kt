package ee.jwl.reaperLedger

import org.bukkit.event.Listener

object EventRegistrar {
    private val events = mutableListOf<Listener>()

    fun register() {
        Main.instance.server.pluginManager.run {
            events.forEach { registerEvents(it, Main.instance) }
        }
    }

    fun addEvent(event: Listener) {
        if (events.contains(event)) {
            return
        }

        events.add(event)
    }

    fun removeEvent(event: Listener) {
        if (events.contains(event)) {
            events.remove(event)
            return
        }

        throw IllegalArgumentException("Event does not exist")
    }
}