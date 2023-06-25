package ir.mmd.linux.utility.font.service

import ir.mmd.linux.utility.font.configDirectoryPath
import ir.mmd.linux.utility.font.div

object LocalCacheService {
	private val cacheFilePath = configDirectoryPath / "caches"
	private val cacheDirectoryPath = configDirectoryPath / "cache"
	
	init {
		cacheFilePath.toFile().createNewFile()
		cacheDirectoryPath.toFile().mkdirs()
	}
}
