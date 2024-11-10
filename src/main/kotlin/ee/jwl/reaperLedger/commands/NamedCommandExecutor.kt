package ee.jwl.reaperLedger.commands

import org.bukkit.command.CommandExecutor

interface NamedCommandExecutor: CommandExecutor {
    val name: String
}