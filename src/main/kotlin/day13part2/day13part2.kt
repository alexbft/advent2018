package day13part2

import bootstrap.Bootstrap

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

private data class Car(var pos: Point, var dir: Direction) {
    var nextTurn = Turn.LEFT
    var crashed = false
}

private enum class Direction(val id: Int, val vector: Point) {
    UP(0, Point(0, -1)),
    RIGHT(1, Point(1, 0)),
    DOWN(2, Point(0, 1)),
    LEFT(3, Point(-1, 0));

    fun turn(turn: Turn): Direction {
        var newId = (id + turn.idChange) % Direction.values().size
        if (newId < 0) {
            newId += Direction.values().size
        }
        return Direction.values().first { it.id == newId }
    }
}

private enum class Turn(val idChange: Int) {
    LEFT(-1),
    STRAIGHT(0),
    RIGHT(1);

    fun next(): Turn {
        return when (this) {
            LEFT -> STRAIGHT
            STRAIGHT -> RIGHT
            RIGHT -> LEFT
        }
    }
}

fun solve(rows: List<String>): Point {
    val cars = mutableListOf<Car>()
    for ((y, row) in rows.withIndex()) {
        for ((x, c) in row.withIndex()) {
            val p = Point(x, y)
            when (c) {
                '^' -> Car(p, Direction.UP)
                '>' -> Car(p, Direction.RIGHT)
                'v' -> Car(p, Direction.DOWN)
                '<' -> Car(p, Direction.LEFT)
                else -> null
            }?.let(cars::add)
        }
    }
    val track = rows.map { row -> row.map { c ->
        when (c) {
            '^', 'v' -> '|'
            '<', '>' -> '-'
            else -> c
        }
    } }
    while (true) {
        val sortedCars = cars.sortedWith(compareBy<Car> { car -> car.pos.y }.thenBy { car -> car.pos.x })
        for (car in sortedCars) {
            if (car.crashed) {
                continue
            }
            car.pos += car.dir.vector
            for (car2 in cars) {
                if (!car2.crashed && car !== car2 && car.pos == car2.pos) {
                    car.crashed = true
                    car2.crashed = true
                    continue
                }
            }
            when (track[car.pos.y][car.pos.x]) {
                '/' -> {
                    car.dir = when (car.dir) {
                        Direction.UP -> Direction.RIGHT
                        Direction.RIGHT -> Direction.UP
                        Direction.DOWN -> Direction.LEFT
                        Direction.LEFT -> Direction.DOWN
                    }
                }
                '\\' -> {
                    car.dir = when (car.dir) {
                        Direction.UP -> Direction.LEFT
                        Direction.RIGHT -> Direction.DOWN
                        Direction.DOWN -> Direction.RIGHT
                        Direction.LEFT -> Direction.UP
                    }
                }
                '+' -> {
                    car.dir = car.dir.turn(car.nextTurn)
                    car.nextTurn = car.nextTurn.next()
                }
            }
        }
        val workingCars = cars.filter { !it.crashed }
        if (workingCars.size <= 1) {
            return workingCars.first().pos
        }
    }
}

fun main(args: Array<String>) {
    val (x, y) = solve(Bootstrap(args).readAllLines())
    println("$x,$y")
}
