package ir.mmd.linux.utility.font

import ir.mmd.linux.utility.font.cmd.*

class CommandLine(cli: Array<String>) {
	class CommandLineException(message: String) : Exception(message)
	
	private val command: Command
	
	init {
		val args = cli.drop(1).filter { !it.startsWith("-") }
		val flags = cli.drop(1).filter { it.startsWith("-") }
		
		command = when (val cmd = cli[0].lowercase()) {
			"help", "h" -> HelpCommand()
			"install", "inst", "i" -> InstallCommand(args, flags)
			"update", "up", "u" -> UpdateCommand()
			"list", "ls", "l" -> ListCommand(flags)
			else -> throw CommandLineException("unknown command: $cmd")
		}
	}
	
	fun execute() = command.execute()
	
	override fun toString() = command.toString()
}
