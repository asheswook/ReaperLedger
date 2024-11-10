package ee.jwl.reaperLedger.observer.playerDeath

import ee.jwl.reaperLedger.entity.DeathLog
import ee.jwl.reaperLedger.observer.Observer
import ee.jwl.reaperLedger.repository.LogRepository
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.*

class LogSaver (
    private val repository: LogRepository
) : Observer<PlayerDeathEvent> {
    override fun update(event: PlayerDeathEvent) {
        val log = DeathLog(event.entity.uniqueId, event.entity.location, Date())
        repository.save(log)
    }
}