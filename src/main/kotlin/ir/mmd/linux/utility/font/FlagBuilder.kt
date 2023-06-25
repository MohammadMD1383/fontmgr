package ir.mmd.linux.utility.font

import java.util.*

class FlagBuilder<T : Enum<T>>(
	enumClass: Class<T>,
	flags: List<String>,
	strToEnum: (String) -> T?
) {
	private val enumSet: EnumSet<T> = EnumSet.noneOf(enumClass)
	
	companion object {
		fun error(message: String): Nothing {
			throw CommandLine.CommandLineException(message)
		}
	}
	
	init {
		flags.forEach {
			when (val dashes = it.takeWhile { c -> c == '-' }.length) {
				2 -> {
					enumSet.add(
						strToEnum(it.drop(dashes))
							?: error("unknown flag: $it")
					)
				}
				
				1 -> {
					it.drop(dashes).forEach { c ->
						enumSet.add(
							strToEnum(c.toString())
								?: error("unknown flag: $it")
						)
					}
				}
				
				else -> {
					error("bad flag: $it")
				}
			}
		}
	}
	
	val result get() = enumSet
}
