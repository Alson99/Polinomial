package ru.als.math.polynom

class Newton constructor(func: MutableMap<Double, Double>): Polynom() {

    // The function to add the node
    fun addNode(node: Double, funVal: Double): Polynom {
        if (_nodes.contains(node)) {
            throw Exception("Can't add existing node")
        }
        val b = mutableListOf(-1.0 * _nodes[_nodes.size - 2], 1.0)
        val z = mutableListOf(_coeff) + (_valueOfW * getDividedDifference(_nodes.size - 1))
        _nodes.add(node)
        _fValues.add(funVal)
        _valueOfW *= Polynom(b) //-1.0 * _nodes[_nodes.size - 2], 1.0 Ce sont les paramtres du polynome
        return Polynom() //*_coeffs.toDoubleArray()) + (_valueOfW * getDividedDifference(_nodes.size - 1)Ce sont les paramtres du polynome
    }

    val a = mutableListOf(1.0)
    private val _nodes: ArrayList<Double> = ArrayList(func.size)
    private val _fValues: ArrayList<Double> = ArrayList(func.size)
    private val _valueOfW: Polynom = Polynom(a) // 1.0 Le parametre qui existait dans le polynome

    init {
        var i = 0
        func.forEach { (node, funVal) ->
            _nodes.add(node)
            _fValues.add(funVal)
            i++
        }

        val newton = Polynom()
        newton += _valueOfW * getDividedDifference(0)
        for (j in 1 until _nodes.size) {
            val g = mutableListOf(-1.0 * _nodes[j - 1], 1.0)
            _valueOfW *= Polynom(g) // -1.0 * _nodes[j - 1], 1.0 Ce sont les parametres du polynome
            newton += _valueOfW * getDividedDifference(j)
        }
        _coeff = newton.coeff.toMutableList()
    }

    private fun getDividedDifference(end: Int): Double {
        var dividedDiff = 0.0
        if (end == 0) {
            return _fValues[0]
        }
        for (i in 0 ..end) {
            var nodesProduct = 1.0
            for (j in 0 .. end) {
                if (j == i) {
                    continue
                }
                nodesProduct *= 1.0 / (_nodes[i] - _nodes[j])
            }
            dividedDiff += _fValues[i] * nodesProduct
        }
        return dividedDiff
    }
}
