package com.phl.calculator

import androidx.compose.runtime.mutableStateOf
import net.objecthunter.exp4j.operator.Operator
import net.objecthunter.exp4j.function.Function
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.*

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

val sin_1: Function = object : Function("sin_1",1){
    override fun apply(vararg args: Double): Double {
        return BigDecimal(Math.toDegrees(asin(args[0]))).divide(BigDecimal(1), 14,RoundingMode.DOWN).toDouble()
    }
}

val cos_1: Function = object : Function("cos_1",1){
    override fun apply(vararg args: Double): Double {
        return BigDecimal(Math.toDegrees(acos(args[0]))).divide(BigDecimal(1), 14,RoundingMode.DOWN).toDouble()
    }
}

val tan_1: Function = object : Function("tan_1",1){
    override fun apply(vararg args: Double): Double {
        return BigDecimal(Math.toDegrees(atan(args[0]))).divide(BigDecimal(1), 14,RoundingMode.DOWN).toDouble()
    }
}

val sinh_1: Function = object : Function("sinh_1",1){
    override fun apply(vararg args: Double): Double {
        return BigDecimal(asinh(args[0])).divide(BigDecimal(1), 14,RoundingMode.DOWN).toDouble()
    }
}

val cosh_1: Function = object : Function("cosh_1",1){
    override fun apply(vararg args: Double): Double {
        return BigDecimal(acosh(args[0])).divide(BigDecimal(1), 14,RoundingMode.DOWN).toDouble()
    }
}

val tanh_1: Function = object : Function("tanh_1",1){
    override fun apply(vararg args: Double): Double {
        return BigDecimal(atanh(args[0])).divide(BigDecimal(1), 14,RoundingMode.DOWN).toDouble()
    }
}

/**
 * 下方是重写sin、cos、tan,角度单位转换 Rad -> Deg
 */
val sin: Function = object : Function("sin",1){
    override fun apply(vararg args: Double): Double {
        if (degState.value) {
            return sin(args[0])
        }

        return BigDecimal(sin(Math.toRadians(args[0]))).divide(BigDecimal(1), 13,RoundingMode.HALF_UP).toDouble()
    }
}

val cos: Function = object : Function("cos",1){
    override fun apply(vararg args: Double): Double {
        if (degState.value) {
            return cos(args[0])
        }
        return BigDecimal(cos(Math.toRadians(args[0]))).divide(BigDecimal(1), 13,RoundingMode.HALF_UP).toDouble()
    }
}

val tan: Function = object : Function("tan",1){
    override fun apply(vararg args: Double): Double {
        if (degState.value) {
            return tan(args[0])
        }
        return BigDecimal(tan(Math.toRadians(args[0]))).divide(BigDecimal(1), 13,RoundingMode.HALF_UP).toDouble()
    }
}

