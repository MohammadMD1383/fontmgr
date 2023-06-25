package ir.mmd.linux.utility.font.service

import io.ktor.client.request.*
import io.ktor.client.statement.*
import ir.mmd.linux.utility.font.configDirectoryPath
import ir.mmd.linux.utility.font.div
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension

object LocalIndexService {
	private val indexFilePath = configDirectoryPath / "index"
	
	init {
		indexFilePath.toFile().createNewFile()
	}
	
	@Suppress("DeferredResultUnused")
	suspend fun update(): Unit = coroutineScope {
		val coroutines = mutableListOf<Deferred<List<String>>>()
		
		LocalSourcesService.getSources().forEach { url ->
			async(IO) {
				println("getting source: $url")
				httpClient.get(url).bodyAsText().lines().mapNotNull { line ->
					line.takeIf { it.isNotBlank() }?.trim()
				}
			}.also { coroutines += it }
		}
		
		val results = coroutines.awaitAll()
		indexFilePath.toFile().outputStream().apply {
			results.forEach { lines ->
				lines.forEach { line ->
					write(line.toByteArray())
					write('\n'.code)
				}
			}
		}
	}
	
	fun getIndexes(): List<String> {
		return indexFilePath.toFile().readLines().mapNotNull {
			it.takeIf { it.isNotBlank() }?.trim()
		}
	}
	
	fun getFontNames(format: ((String) -> String) = { it }): List<String> {
		return getIndexes().map { format(Path(it).nameWithoutExtension) }
	}
}
