package ir.mmd.linux.utility.font.service

import ir.mmd.linux.utility.font.n.FontConfig
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension

object FontService {
	@Suppress("NOTHING_TO_INLINE")
	inline fun isSystemFont(path: String) = path.startsWith("/usr/")
	
	@Suppress("NOTHING_TO_INLINE")
	inline fun isUserFont(path: String) = path.startsWith("/home/")
	
	@Suppress("NOTHING_TO_INLINE")
	inline fun getFontName(path: String) = Path(path).nameWithoutExtension
	
	fun getAllFonts(format: (path: String, name: String) -> String = { _, n -> n }): List<String> {
		return FontConfig.getAllFontPaths().map { format(it, getFontName(it)) }
	}
	
	fun getSystemFonts(format: (path: String, name: String) -> String = { _, n -> n }): List<String> {
		return FontConfig.getAllFontPaths().mapNotNull {
			if (!isSystemFont(it)) null else format(it, getFontName(it))
		}
	}
	
	fun getUserFonts(format: (path: String, name: String) -> String = { _, n -> n }): List<String> {
		return FontConfig.getAllFontPaths().mapNotNull {
			if (!isUserFont(it)) null else format(it, getFontName(it))
		}
	}
}
