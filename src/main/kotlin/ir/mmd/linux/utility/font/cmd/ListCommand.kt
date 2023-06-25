package ir.mmd.linux.utility.font.cmd

import ir.mmd.linux.utility.font.FlagBuilder
import ir.mmd.linux.utility.font.service.FontService
import ir.mmd.linux.utility.font.service.FontService.isSystemFont
import ir.mmd.linux.utility.font.service.LocalIndexService
import java.util.*

class ListCommand(
	flags: List<String>
) : Command {
	private enum class Flag {
		/**
		 * List all fonts
		 */
		All,
		
		/**
		 * List system-wide installed fonts
		 */
		System,
		
		/**
		 * List user-specific installed fonts
		 */
		User,
		
		/**
		 * List fonts in local index
		 */
		Cache
	}
	
	private val flags: EnumSet<Flag> = FlagBuilder(Flag::class.java, flags) {
		when (it) {
			"all", "a" -> Flag.All
			"system", "sys", "s" -> Flag.System
			"user", "usr", "u" -> Flag.User
			"cache", "c" -> Flag.Cache
			else -> null
		}
	}.result
	
	override fun execute() {
		if (flags.isEmpty() || Flag.All in flags) {
			printLocalIndexFonts()
			printLocalMachineFonts()
			return
		}
		
		if (Flag.Cache in flags) {
			printLocalIndexFonts()
		}
		
		if (Flag.System in flags && Flag.User in flags) {
			printLocalMachineFonts()
			return
		}
		
		if (Flag.System in flags) {
			FontService.getSystemFonts { _, n -> "[SYSTEM] $n" }.forEach(::println)
		}
		
		if (Flag.User in flags) {
			FontService.getUserFonts { _, n -> "[USER] $n" }.forEach(::println)
		}
	}
	
	private fun printLocalIndexFonts() {
		LocalIndexService.getFontNames { "[INDEX] $it" }.forEach(::println)
	}
	
	private fun printLocalMachineFonts() {
		FontService.getAllFonts { p, n -> if (isSystemFont(p)) "[SYSTEM] $n" else "[USER] $n" }.forEach(::println)
	}
}
