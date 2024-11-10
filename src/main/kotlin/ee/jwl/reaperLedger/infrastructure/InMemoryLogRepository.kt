package ee.jwl.reaperLedger.infrastructure

import ee.jwl.reaperLedger.entity.DeathLog
import ee.jwl.reaperLedger.repository.LogRepository
import java.util.*

object InMemoryLogRepository : LogRepository {
    private val logs = mutableListOf<DeathLog>()

    override fun find(uuid: UUID): List<DeathLog> {
        return logs.filter { it.getId() == uuid }
    }

    override fun findLatestByPlayer(playerId: UUID, limit: Int?): List<DeathLog> {
        if (limit == null) {
            return logs.filter { it.getUserId() == playerId }
        }

        return logs.filter { it.getUserId() == playerId }.takeLast(limit)
    }

    override fun save(log: DeathLog) {
        logs.add(log)
    }
}