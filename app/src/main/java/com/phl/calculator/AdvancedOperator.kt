package com.phl.calculator

import net.objecthunter.exp4j.operator.Operator
import net.objecthunter.exp4j.function.Function
import kotlin.math.ln
import kotlin.math.log10

val factorial: Operator = object : Operator("!", 1, true, PRECEDENCE_POWER + 1) {
    override fun apply(vararg args: Double): Double {
        val arg = args[0].toInt()
        require(arg.toDouble() == args[0]) { "Operand for factorial has to be an integer" }
        require(arg >= 0) { "The operand of the factorial can not be less than zero" }
        var result = 1.0
        for (i in 1..arg) {
            result *= i.toDouble()
        }
        return result
    }
}
val ln: Function = object : Function("ln", 1) {
    override fun apply(vararg args: Double): Double {
        return ln(args[0])
    }
}
val lg: Function = object : Function("lg", 1) {
    override fun apply(vararg args: Double): Double {
        return log10(args[0])
    }
}

val rand: Function = object : Function("Rand",0){
    override fun apply(vararg args: Double): Double {
        return Math.random()
    }
}


var MEMORY_NUMBER = "0"

val mc: Function = object : Function("mc",0){
    override fun apply(vararg args: Double): Double {
        MEMORY_NUMBER = "0"
        return 0.0
    }
}

val mr: Function = object : Function("mr",0){
    override fun apply(vararg args: Double): Double {
        return MEMORY_NUMBER.toDouble()
    }
}

val mPlus: Operator = object : Operator("++", 1, true, PRECEDENCE_POWER + 2) {
    override fun apply(vararg args: Double): Double {
        val plus = args[0].plus(MEMORY_NUMBER.toDouble())
        MEMORY_NUMBER = plus.toString()
        return plus
    }
}

val mMinus: Operator = object : Operator("--", 1, true, PRECEDENCE_POWER + 3) {
    override fun apply(vararg args: Double): Double {
        val minus = MEMORY_NUMBER.toDouble().minus(args[0])
        MEMORY_NUMBER = minus.toString()
        return minus
    }
}

