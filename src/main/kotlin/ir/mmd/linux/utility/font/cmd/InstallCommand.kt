package ir.mmd.linux.utility.font.cmd

import ir.mmd.linux.utility.font.FlagBuilder
import java.util.*

class InstallCommand(
	private val fonts: List<String>,
	flags: List<String>
) : Command {
	enum class Flag {
		/**
		 * System install
		 */
		System,
		
		/**
		 * User install
		 */
		User
	}
	
	private val flags: EnumSet<Flag> = FlagBuilder(Flag::class.java, flags) {
		when (it) {
			"system", "sys", "s" -> Flag.System
			"user", "usr", "u" -> Flag.User
			else -> null
		}
	}.result
	
	override fun execute() {
	
	}
}
