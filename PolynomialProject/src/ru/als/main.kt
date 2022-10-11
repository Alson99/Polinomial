package ru.als

import ru.als.math.polynom.Lagrange
import ru.als.math.polynom.Newton
import ru.als.math.polynom.Polynom
import kotlin.system.measureTimeMillis

fun main() {

    val coeffs3 = HashMap<Double, Double>()
    coeffs3[0.0] = -1.0
    coeffs3[1.0] = 1.0
    println(Newton(coeffs3))
    println(Newton(coeffs3).addNode(0.5, 1.0))

    // Let's create the variable which will help us to calculate the time of Lagrange and Newton
    val coefficients = HashMap<Double, Double>()
    // The cycle from 1 to 1000
    for (i in 0..1000) {
        coefficients[i.toDouble()] = (-1000..1000).random().toDouble()
    }

    // Time calculations for Lagrange and for newton
    var timeLagrange = measureTimeMillis {
        Lagrange(coefficients)
    }
    println("Lagrange: $timeLagrange")

    var timeNewton = measureTimeMillis {
        Newton(coefficients)
    }
    println("Newton: $timeNewton")

    // add node for Lagrange
    coefficients[-1.0] = 312.0
    timeLagrange = measureTimeMillis {
        Lagrange(coefficients)
    }
    println("Lagrange add node: $timeLagrange")

    // Add node for Newton
    timeNewton = measureTimeMillis {
        Newton(coefficients)
    }
    println("Newton add node: $timeNewton")
}