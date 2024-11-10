package ee.jwl.reaperLedger.repository

import ee.jwl.reaperLedger.entity.DeathLog
import java.util.UUID

interface LogRepository {
    fun find(uuid: UUID): List<DeathLog>
    fun findLatestByPlayer(playerId: UUID, limit: Int? = null): List<DeathLog>
    fun save(log: DeathLog)
}