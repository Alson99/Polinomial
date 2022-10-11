package ru.als.math.polynom

class Lagrange constructor(func: MutableMap<Double, Double>) : Polynom() {

    init {
        val lagrange = Polynom()
        func.forEach { (node, funVal) ->
            lagrange += fundamentalLagrangePolynomial(func, node) * funVal
        }
        _coeff = lagrange.coeff.toMutableList()
    }

    // Function for calculating the fundamental of polynomial of Lagrange
    private fun fundamentalLagrangePolynomial(
        func: MutableMap<Double, Double>,
        currNode: Double
    ): Polynom {
        val h = mutableListOf(1.0)
        val polynomial = Polynom(h)
        func.forEach { (node, _) ->
            if (node != currNode) {
                val j = mutableListOf(-1.0 * node / (currNode - node), 1.0 / (currNode - node))
                polynomial *= Polynom(j) // Les parametres de cette polynome -1.0 * node / (currNode - node), 1.0 / (currNode - node)
            }
        }
        return polynomial
    }
}





