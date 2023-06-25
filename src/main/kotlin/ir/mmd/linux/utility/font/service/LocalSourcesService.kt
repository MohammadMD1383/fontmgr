package ir.mmd.linux.utility.font.service

import ir.mmd.linux.utility.font.configDirectoryPath
import ir.mmd.linux.utility.font.div

object LocalSourcesService {
	private val sourcesFilePath = configDirectoryPath / "sources"
	
	init {
		sourcesFilePath.toFile().createNewFile()
	}
	
	fun getSources(): List<String> {
		return sourcesFilePath.toFile().readLines().mapNotNull {
			it.takeIf { it.isNotBlank() }?.trim()
		}
	}
}
