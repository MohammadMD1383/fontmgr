package ir.mmd.linux.utility.font.cmd

class HelpCommand : Command {
	override fun execute() {
		println("""
			╔═══════════════════════════════════════ MANUAL ═══════════════════════════════════════╗
			║ COMMANDS:                                                                            ║
			║ --------------------------------------                                               ║
			║ 1. install (inst, i)                                                                 ║
			╚══════════════════════════════════════════════════════════════════════════════════════╝
		""".trimIndent())
	}
}
