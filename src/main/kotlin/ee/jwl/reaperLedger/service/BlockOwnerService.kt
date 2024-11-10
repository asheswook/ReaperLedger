package ee.jwl.reaperLedger.service

import ee.jwl.reaperLedger.Main
import org.bukkit.NamespacedKey
import org.bukkit.block.TileState
import org.bukkit.persistence.PersistentDataType
import java.util.UUID

object BlockOwnerService {
    fun isOwner(block: TileState, playerId: UUID): Boolean {
        val key = NamespacedKey(Main.instance, "death_owner")
        val owner = block.persistentDataContainer.get(key, PersistentDataType.STRING)
        if (owner == null) { // Everyone can access the block if there is no owner
            return true
        }
        return owner == playerId.toString()
    }

    fun setOwner(block: TileState, playerId: UUID) {
        val key = NamespacedKey(Main.instance, "death_owner")
        block.persistentDataContainer.set(key, PersistentDataType.STRING, playerId.toString())

        block.update()
    }

    fun removeOwner(block: TileState) {
        val key = NamespacedKey(Main.instance, "death_owner")
        block.persistentDataContainer.remove(key)

        block.update()
    }

    fun getOwner(block: TileState): UUID? {
        val key = NamespacedKey(Main.instance, "death_owner")
        val owner = block.persistentDataContainer.get(key, PersistentDataType.STRING)
        return owner?.let { UUID.fromString(it) }
    }
}