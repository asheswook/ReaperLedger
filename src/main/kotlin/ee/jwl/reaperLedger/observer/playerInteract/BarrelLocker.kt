package ee.jwl.reaperLedger.observer.playerInteract

import ee.jwl.reaperLedger.observer.Observer
import ee.jwl.reaperLedger.service.BlockOwnerService
import org.bukkit.Material
import org.bukkit.block.Barrel
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class BarrelLocker : Observer<PlayerInteractEvent> {
    override fun update(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK && event.action != Action.LEFT_CLICK_BLOCK) return
        if (event.clickedBlock?.type != Material.BARREL) return
        if (event.hand == EquipmentSlot.OFF_HAND) return

        // TODO: 관리자 로직
        // TODO: 활성화 비활성화 로직

        val isOwner = BlockOwnerService.isOwner(event.clickedBlock!!.state as Barrel, event.player.uniqueId)
        if (!isOwner) {
            event.isCancelled = true
            event.player.sendMessage("You are not the owner of this barrel")
        }
    }
}