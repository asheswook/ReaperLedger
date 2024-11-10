package ee.jwl.reaperLedger.entity

import org.bukkit.Location
import java.text.DateFormat
import java.util.Date
import java.util.UUID

class DeathLog (
    private val playerId: UUID,
    private val location: Location,
    private val deadAt: Date,
) {
    private val id = UUID.randomUUID()

    fun getId(): UUID = id
    fun getUserId(): UUID = playerId
    fun getLocation(): Location = location
    fun getFormattedLocation(): String = "${location.world!!.name} ${location.blockX} ${location.blockY} ${location.blockZ}"
    fun getDeadAt(): Date = deadAt
    fun getFormattedDeadAt(): String = DateFormat.getDateTimeInstance().format(deadAt)
}