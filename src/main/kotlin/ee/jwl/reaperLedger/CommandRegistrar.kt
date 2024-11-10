package ee.jwl.reaperLedger

import ee.jwl.reaperLedger.commands.NamedCommandExecutor

object CommandRegistrar {
    private val commandExecutors = mutableListOf<NamedCommandExecutor>()

    fun register() {
        commandExecutors.forEach {
            Main.instance.server.getPluginCommand(it.name)!!.setExecutor(it)
        }
    }

    fun addCommand(command: NamedCommandExecutor) {
        if (commandExecutors.contains(command)) {
            return
        }

        commandExecutors.add(command)
    }

    fun removeCommand(command: NamedCommandExecutor) {
        if (commandExecutors.contains(command)) {
            commandExecutors.remove(command)
            return
        }

        throw IllegalArgumentException("Command does not exist")
    }
}