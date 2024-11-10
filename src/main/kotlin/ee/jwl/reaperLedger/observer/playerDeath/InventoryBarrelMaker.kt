package ee.jwl.reaperLedger.observer.playerDeath

import ee.jwl.reaperLedger.Main
import ee.jwl.reaperLedger.observer.Observer
import ee.jwl.reaperLedger.service.BlockOwnerService
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Barrel
import org.bukkit.block.BlockFace
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.block.data.Directional

class InventoryBarrelMaker : Observer<PlayerDeathEvent> {
    override fun update(event: PlayerDeathEvent) {
        val loc = findTopLiquidLocation(event.entity.location)

        val barrel = createBarrelAtLocation(loc)
        barrel?.let { BlockOwnerService.setOwner(it, event.entity.uniqueId) }

        barrel?.let { firstBarrel ->
            var secondaryBarrel: Barrel? = null
            if (event.drops.count() > firstBarrel.inventory.contents.size) {
                val secondaryLoc = loc.clone().add(0.0, 1.0, 0.0)
                secondaryBarrel = createBarrelAtLocation(secondaryLoc)
                secondaryBarrel?.let { BlockOwnerService.setOwner(it, event.entity.uniqueId) }
            }

            event.drops.forEach { drop ->
                if (firstBarrel.inventory.firstEmpty() == -1) {
                    if (secondaryBarrel == null) {
                        throw Exception("No secondary barrel found")
                    }

                    secondaryBarrel.inventory.addItem(drop)
                    return@forEach
                }

                firstBarrel.inventory.addItem(drop)
            }

            event.drops.clear()
        }
    }

    private fun createBarrelAtLocation(location: Location): Barrel? {
        try {
            val block = location.block
            block.type = Material.BARREL

            val blockData = block.blockData.clone()
            if (blockData is Directional) {
                blockData.facing = BlockFace.UP
                block.blockData = blockData
            }

            return (block.state as Barrel)
        } catch (e: Exception) {
            Main.instance.logger.info("Failed to create barrel at location: $location")
            Main.instance.logger.info(e.message)
            return null
        }
    }

    private tailrec fun findTopLiquidLocation(location: Location): Location {
        val world = location.world ?: return location

        if (location.block.y >= world.maxHeight - 1) {
            return location
        }

        val aboveBlock = location.add(0.0, 1.0, 0.0)

        if (aboveBlock.block.type.isSolid || !aboveBlock.block.isLiquid) {
            return location.subtract(0.0, 1.0, 0.0)
        }

        return findTopLiquidLocation(aboveBlock)
    }
}