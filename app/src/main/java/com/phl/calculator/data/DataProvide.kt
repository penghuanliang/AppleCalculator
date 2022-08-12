package com.phl.calculator.data

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import com.phl.calculator.R
import com.phl.calculator.ui.theme.*

/**
 * 计算器上所有的按钮数据都由此提供，包括控件的字体颜色、背景、宽高
 */
object DataProvide {

    fun generateHorizontalData(
        context: Context,
        functionState: Boolean,
        degState: Boolean,
        onClick: (value: Any) -> Unit = {}
    ): List<List<ButtonData>> {

        //控件宽高计算
        val btnWidth = context.resources.configuration.screenWidthDp / 10
        val btnHeight = context.resources.configuration.screenHeightDp / 6

        //控件背景颜色
        val bgColor = Color21
        val bgGray = Color33
        val bgLightGray = ColorA6
        val bgOrange = ColorOrange


        val list = mutableListOf<List<ButtonData>>()

        //第一列数据
        list.add(
            listOf(
                ButtonData("(", btnWidth, btnHeight, Color.White, bgColor, onClick),
                ButtonData(")", btnWidth, btnHeight, Color.White, bgColor, onClick),
                ButtonData("mc", btnWidth, btnHeight, Color.White, bgColor, onClick),
                ButtonData("m+", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("++") }),
                ButtonData("m-", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("--") }),
                ButtonData("mr", btnWidth, btnHeight, Color.White, bgColor, onClick),
                ButtonData("AC", btnWidth, btnHeight, Color.Black, bgLightGray, onClick),
                ButtonData("+/_", btnWidth, btnHeight, Color.Black, bgLightGray, onClick),
                ButtonData("%", btnWidth, btnHeight, Color.Black, bgLightGray, onClick),
                ButtonData("÷", btnWidth, btnHeight, Color.White, bgOrange, onClick),
            )
        )

        //第二列数据
        list.add(
            listOf(
                if (functionState){
                    ButtonData("2nd", btnWidth, btnHeight, Color.Black, Color80, onClick)
                }  else {
                    ButtonData("2nd", btnWidth, btnHeight, Color.White, bgColor, onClick)
                },
                ButtonData("x²", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("^2") }),
                ButtonData("x³", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("^3") }),
                ButtonData("xⁿ", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("^") }),
                ButtonData("e×", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("e^") }),
                ButtonData("10×", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("10^") }),
                ButtonData("7", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("8", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("9", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("×", btnWidth, btnHeight, Color.White, bgOrange, onClick),
            )
        )


        //第三列数据
        list.add(
            listOf(
                ButtonData("⅟x", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("1/(") }),
                ButtonData("²√x", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("√") }),
                ButtonData("³√x", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("^(1/3)") }),
                ButtonData("ⁿ√x", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("^(1/") }),
                ButtonData("ln", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("ln(") }),
                ButtonData("log₁₀", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("lg(") }),
                ButtonData("4", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("5", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("6", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("-", btnWidth, btnHeight, Color.White, bgOrange, onClick),
            )
        )

        //第四列数据
        list.add(
            listOf(
                ButtonData("x!", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("!") }),
                ButtonData(if (functionState) "sin⁻¹" else "sin", btnWidth, btnHeight, Color.White, bgColor, onClick = { if (functionState) onClick("sin_1(") else onClick("sin(")}),
                ButtonData(if (functionState) "cos⁻¹" else "cos", btnWidth, btnHeight, Color.White, bgColor, onClick = { if (functionState) onClick("cos_1(") else onClick("cos(")}),
                ButtonData(if (functionState) "tan⁻¹" else "tan", btnWidth, btnHeight, Color.White, bgColor, onClick = { if (functionState) onClick("tan_1(") else onClick("tan(")}),
                ButtonData("e", btnWidth, btnHeight, Color.White, bgColor, onClick),
                ButtonData("EE", btnWidth, btnHeight, Color.White, bgColor, onClick = { onClick("×10^") }),
                ButtonData("1", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("2", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("3", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("+", btnWidth, btnHeight, Color.White, bgOrange, onClick),
            )
        )

        //第五列数据
        list.add(
            listOf(
                ButtonData(if (degState) "Deg" else "Rad", btnWidth, btnHeight, Color.White, bgColor, onClick = { if (degState) onClick("Deg") else onClick("Rad") }),
                ButtonData(if (functionState) "sinh⁻¹" else "sinh", btnWidth, btnHeight, Color.White, bgColor, onClick = { if (functionState) onClick("sinh_1(") else onClick("sinh(")}),
                ButtonData(if (functionState) "cosh⁻¹" else "cosh", btnWidth, btnHeight, Color.White, bgColor, onClick = { if (functionState) onClick("cosh_1(") else onClick("cosh(")}),
                ButtonData(if (functionState) "tanh⁻¹" else "tanh", btnWidth, btnHeight, Color.White, bgColor, onClick = { if (functionState) onClick("tanh_1(") else onClick("tanh(")}),
                ButtonData("π", btnWidth, btnHeight, Color.White, bgColor, onClick),
                ButtonData("Rand", btnWidth, btnHeight, Color.White, bgColor, onClick),
                ButtonData("0", btnWidth * 2, btnHeight, Color.White, bgGray, onClick),
                ButtonData(".", btnWidth, btnHeight, Color.White, bgGray, onClick),
                ButtonData("=", btnWidth, btnHeight, Color.White, bgOrange, onClick),
            )
        )

        return list
    }


    fun generateVerticalData(
        context: Context,
        onClick: (value: Any) -> Unit = {}
    ): List<List<ButtonData>> {

        //控件宽高计算
        val btnWidth = context.resources.configuration.screenWidthDp / 4

        //控件背景颜色
        val bgGray = colorResource(context, R.color.bg_gray)
        val bgLightGray = colorResource(context, R.color.bg_light_gray)
        val bgOrange = colorResource(context, R.color.bg_orange)


        val list = mutableListOf<List<ButtonData>>()

        //第一列数据
        list.add(
            listOf(
                ButtonData("AC", btnWidth, btnWidth, Color.Black, bgLightGray, onClick),
                ButtonData("+/_", btnWidth, btnWidth, Color.Black, bgLightGray, onClick),
                ButtonData("%", btnWidth, btnWidth, Color.Black, bgLightGray, onClick),
                ButtonData("÷", btnWidth, btnWidth, Color.White, bgOrange, onClick),
            )
        )

        //第二列数据
        list.add(
            listOf(
                ButtonData("7", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("8", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("9", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("×", btnWidth, btnWidth, Color.White, bgOrange, onClick),
            )
        )


        //第三列数据
        list.add(
            listOf(
                ButtonData("4", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("5", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("6", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("-", btnWidth, btnWidth, Color.White, bgOrange, onClick),
            )
        )

        //第四列数据
        list.add(
            listOf(
                ButtonData("1", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("2", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("3", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("+", btnWidth, btnWidth, Color.White, bgOrange, onClick),
            )
        )

        //第五列数据
        list.add(
            listOf(
                ButtonData("0", btnWidth * 2, btnWidth, Color.White, bgGray, onClick),
                ButtonData(".", btnWidth, btnWidth, Color.White, bgGray, onClick),
                ButtonData("=", btnWidth, btnWidth, Color.White, bgOrange, onClick),
            )
        )

        return list
    }


    private fun colorResource(context: Context, @ColorRes id: Int): Color {
        return if (Build.VERSION.SDK_INT >= 23) {
            Color(context.resources.getColor(id, context.theme))
        } else {
            @Suppress("DEPRECATION")
            Color(context.resources.getColor(id))
        }
    }


    fun intList(): List<String>  = listOf("0","1","2","3","4","5","6","7","8","9", ".")
    fun prefixSymbolList(): List<String>  = listOf("sin(","cos(","tan(","e^","10^","1/(","ln(","lg(", "√", "sinh(","cosh(","tanh(","sin_1(","cos_1(", "tan_1(","sinh_1(","cosh_1(", "tanh_1(")
    fun postfixSymbolList(): List<String> = listOf("!","^2", "^3", "^(1/3)", "++", "--")
    fun easySymbolList(): List<String> = listOf("π", "e", "Rand", "mc", "mr")
    fun multiInputList(): List<String> = listOf("^", "^(1/","+", "-", "×", "÷", "×10^")
}