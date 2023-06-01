package day22part1

data class Point(val x: Int, val y: Int)

private class Cave(private val depth: Int, private val target: Point) {
    private val width = target.x + 1
    private val height = target.y + 1
    private val erosion = Array(width * height) { 0 }

    init {
        for (y in 0..target.y) {
            for (x in 0..target.x) {
                erosion[y * width + x] = calcErosion(x, y)
            }
        }
    }

    private fun calcErosion(x: Int, y: Int): Int {
        return if (x == 0 && y == 0 || x == target.x && y == target.y) {
            getErosionByGeoIndex(0)
        } else if (x == 0) {
            getErosionByGeoIndex(y * 48271)
        } else if (y == 0) {
            getErosionByGeoIndex(x * 16807)
        } else {
            getErosionByGeoIndex(getErosion(x - 1, y) * getErosion(x, y - 1))
        }
    }

    private fun getErosion(x: Int, y: Int): Int {
        return erosion[y * width + x]
    }

    private fun getErosionByGeoIndex(geoIndex: Int): Int {
        return (geoIndex + depth) % 20183
    }

    fun getRegionType(x: Int, y: Int): Int {
        return getErosion(x, y) % 3
    }
}

fun solve(depth: Int, target: Point): Int {
    val cave = Cave(depth, target)
    var result = 0
    for (y in 0..target.y) {
        for (x in 0..target.x) {
            result += cave.getRegionType(x, y)
        }
    }
    return result
}

fun main() {
    println(solve(7305, Point(13, 734)))
}
