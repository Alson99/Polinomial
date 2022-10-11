package ru.als.math.polynom

import java.lang.StringBuilder
import kotlin.math.abs
import ru.als.math.*
import kotlin.math.max

open class Polynom(coeff: Collection<Double>) {

    var _coeff: MutableList<Double>
    //protected lateinit var coef: MutableList<Double>

    val coeff: List<Double>
        get() = _coeff.toList()

    val degree: Int
        get() = coeff.size - 1

    init{
        _coeff = coeff.toMutableList()
        removeZeros()
    }
    constructor(coeff: Array<Double>) : this(coeff.toList())
    constructor(coeff: DoubleArray): this(coeff.toList())

    constructor() : this(mutableListOf(0.0))

    private fun removeZeros(){
        var found = false
        val nc = coeff.reversed().filter{
            if (found || it neq 0.0) {found = true; true} else false
        }
        _coeff.clear()
        _coeff.addAll(nc.reversed())
        if (_coeff.size == 0) _coeff.add(0.0)
    }

    operator fun plus(other: Polynom) =
        Polynom(DoubleArray(max(degree, other.degree)+1){
            (if (it <= degree) _coeff[it] else 0.0) +
            if (it <= other.degree) other._coeff[it] else 0.0
        })

    operator fun plus(value: Double)  =
        Polynom(
            DoubleArray(degree+1){ _coeff[it] + if (it == 0) value else 0.0}
        )

    operator fun plusAssign(other: Polynom){
        _coeff.indices.forEach {
            _coeff[it] += if (it <= other.degree) other._coeff[it] else 0.0
        }
        for (i in degree+1..other.degree){
            _coeff.add(other._coeff[i])
        }
        removeZeros()
    }

    operator fun plusAssign(value: Double){
        _coeff[0] += value
    }

    operator fun minus(other: Polynom) = this + other * -1.0

    operator fun minus(value: Double) = this + value * -1.0

    operator fun minusAssign(other: Polynom){
        this.plusAssign(other)
    }

    operator fun minusAssign(value: Double){
        this.plusAssign(-value)
    }

    operator fun times(value: Double) =
        Polynom(DoubleArray(degree + 1){_coeff[it] * value})

  /*  operator fun timesAssign(value: Polynom){
        _coeff.indices.forEach { _coeff[it] *= value }
        removeZeros()
    }*/

    operator fun timesAssign(other: Polynom) {
        this._coeff = (this * other)._coeff
    }

    operator fun times(other: Polynom): Polynom{
        val t = DoubleArray(degree + other.degree + 1){ 0.0 }
        _coeff.forEachIndexed { ti, tc ->
            other._coeff.forEachIndexed{ oi, oc ->
                t[ti + oi] += tc * oc
            }
        }
        return Polynom()
    }

    operator fun div(k: Double) : Polynom? =
        if (k.compareTo(0.0)!=0)
            this*(1.0/k)
        else
            null

    operator fun invoke(x: Double): Double{
        var pow = 1.0
        return _coeff.reduce{ acc, d ->
            pow *=x; acc + d * pow
        }
    }

    override fun equals(other: Any?) : Boolean {
        if (other !is Polynom) {
            return false
        }
        if (other.degree != this.degree) {
            return false
        }
        for (i in 0 until this.degree) {
            if (other._coeff[i] != this._coeff[i]) {
                return false
            }
        }
        return true
    }

    override fun hashCode(): Int {
        return _coeff.hashCode()
    }

    override fun toString(): String {
        val res = StringBuilder()
        val pow = degree
        fun isLong(x: Double) = abs(x-x.toLong()).compareTo(0.0) == 0
        coeff.reversed().forEachIndexed { ind, v ->
            val i = pow - ind
            if (v.compareTo(0.0) != 0 || pow == 0) {
                res.append(if (v < 0) "-" else if (i < pow) "+" else "")
                val c = abs(v)
                if (c.compareTo(1.0) != 0 || i == 0)
                    res.append(if (isLong(c)) c.toLong() else c)
                if (i >= 1) {
                    res.append('x')
                    if (i > 1) {
                        res.append("^$i")
                    }
                }
            }
        }
        return res.toString()
    }
}