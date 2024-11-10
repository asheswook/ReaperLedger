package ee.jwl.reaperLedger.commands

import ee.jwl.reaperLedger.repository.LogRepository
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class DeathCommand (
    private val repository: LogRepository
) : NamedCommandExecutor {

    override val name: String
        get() = "death"

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is ConsoleCommandSender) {
            sender.sendMessage("Console cannot use this command")
            return true
        }

        if (sender !is Player) {
            sender.sendMessage("Only players can use this command")
            return true
        }

        val player = sender.player!!
        val logs = repository.findLatestByPlayer(player.uniqueId, 5)
        if (logs.isEmpty()) {
            sender.sendMessage("You have no death logs.")
            return true
        }

        sender.sendMessage("§6Showing latest 5 death logs:")
        logs.forEach {
            sender.sendMessage("§8----------------------------§r")
            sender.sendMessage(it.getFormattedDeadAt())
            sender.sendMessage(it.getFormattedLocation())
        }
        sender.sendMessage("§8----------------------------§r")

        return true
    }
}