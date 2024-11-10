package ee.jwl.reaperLedger.entity

import org.bukkit.inventory.ItemStack

class DeathInventory (
    private val items: Array<ItemStack>,
) {
    fun getItems(): Array<ItemStack> = items
}