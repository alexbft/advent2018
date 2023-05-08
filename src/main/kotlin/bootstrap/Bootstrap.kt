package bootstrap

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class Bootstrap(args: Array<String>) {
    val inputFilePath: Path = Paths.get("resources", args[0]).toAbsolutePath().normalize()

    fun readAllText(): String {
        return File(inputFilePath.toString()).readText().trimEnd()
    }

    fun readAllLines(): List<String> {
        return File(inputFilePath.toString()).readLines()
    }
}
