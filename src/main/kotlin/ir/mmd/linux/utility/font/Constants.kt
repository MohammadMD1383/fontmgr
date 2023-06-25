package ir.mmd.linux.utility.font

import java.nio.file.Path
import kotlin.io.path.Path

val userHomePath = Path(System.getProperty("user.home"))
val configDirectoryPath = userHomePath / ".font"

operator fun Path.div(path: Path) = Path(this.toString(), path.toString())
operator fun Path.div(path: String) = Path(this.toString(), path)
