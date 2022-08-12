package com.phl.calculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phl.calculator.data.DataProvide
import com.phl.calculator.ext.formatStrList
import com.phl.calculator.ext.stripTrailingZeros
import com.phl.calculator.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.math.BigDecimal

class MainActivity : ComponentActivity() {

    private val saveValueState      = mutableStateOf("")
    private val currentValueState   = mutableStateListOf("0")
    private val operatorState       = mutableStateOf("")
    private val orientationState    by lazy { mutableStateOf(resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) }
    private val completeState       = mutableStateOf(false)
    private val highlightSymbol     = mutableStateOf("")
    private val secondFunction      = mutableStateOf(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(saveValueState, currentValueState, operatorState, orientationState, completeState, highlightSymbol, secondFunction, degState)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                orientationState.value = false
            }
            else -> {
                orientationState.value = true
            }
        }
    }
}

@Composable
fun Greeting(
    saveValueState: MutableState<String>,
    currentValueState: SnapshotStateList<String>,
    operatorState: MutableState<String>,
    orientationState: MutableState<Boolean>,
    completeState: MutableState<Boolean>,
    highlightSymbol: MutableState<String>,
    secondFunction: MutableState<Boolean>,
    degState: MutableState<Boolean>
) {
    val historyComponent = remember { saveValueState }
    val formulaComponent = remember { currentValueState }
    val operator         = remember { operatorState }
    val expand           = remember { orientationState }
    val complete         = remember { completeState }
    val symbol           = remember { highlightSymbol }
    val secondFun        = remember { secondFunction }
    val deg              = remember { degState }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            TopTextContainer(
                expand = expand,
                list = formulaComponent,
                degState = deg
            )

            NumPad(
                modifier = Modifier.padding(bottom = 10.dp),
                expand = expand,
                historyComponent = historyComponent,
                list = formulaComponent,
                operator = operator,
                complete = complete,
                highlightSymbol = symbol,
                secondFunction = secondFun,
                degState = deg
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val orientationState = remember {
        mutableStateOf(false)
    }
    val saveValueState = remember {
        mutableStateOf("")
    }
    val currentValueState = remember {
        mutableStateListOf("0")
    }
    val operatorState = remember {
        mutableStateOf("")
    }
    val completeState = remember {
        mutableStateOf(false)
    }
    val highlightSymbol = remember {
        mutableStateOf("")
    }
    val secondFunction = remember {
        mutableStateOf(false)
    }
    val degState = remember {
        mutableStateOf(false)
    }


    CalculatorTheme {
        Greeting(saveValueState, currentValueState, operatorState, orientationState, completeState, highlightSymbol, secondFunction, degState)
    }
}

@Composable
fun NumPad(
    modifier: Modifier = Modifier,
    expand: MutableState<Boolean>,
    historyComponent: MutableState<String>,
    list: SnapshotStateList<String>,
    operator: MutableState<String>,
    complete: MutableState<Boolean>,
    highlightSymbol: MutableState<String>,
    secondFunction: MutableState<Boolean>,
    degState: MutableState<Boolean>
) {
    var currentOperation = ""
    val onClick: (value: Any) -> Unit = { value: Any ->
        var currentValue = list.formatStrList()

        if (historyComponent.value.isNotBlank() && value !in DataProvide.multiInputList()) {
            highlightSymbol.value = ""
        }

        when (value) {
            "+/_" -> {
                currentValue.let {
                    if (it.startsWith("-")) {
                        list.removeAt(0)
                    } else {
                        list.add(0, "-")
                    }
                }
            }

            "%" -> {
                try {
                    val result = BigDecimal(currentValue).divide(BigDecimal(100)).stripTrailingZeros().toString()
                    list.clear()
                    list.add(result)
                    historyComponent.value = ""
                    complete.value = true
                } catch (e: Exception) {
                    list.clear()
                    list.add("错误")
                    historyComponent.value = ""
                    operator.value = ""
                    complete.value = true
                }
            }

            "=" -> {
                try {
                    val result = calculate("${historyComponent.value}${operator.value}$currentValue").stripTrailingZeros()
                    list.clear()
                    list.add(result)
                    historyComponent.value = ""
                    operator.value = ""
                    complete.value = true
                } catch (e: Exception) {
                    list.clear()
                    list.add("错误")
                    historyComponent.value = ""
                    operator.value = ""
                    complete.value = true
                }
            }
            "AC" -> {
                list.clear()
                list.add("0")
                historyComponent.value = ""
                operator.value = ""
            }

            in DataProvide.intList() -> {
                // 考虑计算完成,重新输入数字
                if (complete.value && operator.value.isBlank()) {
                    historyComponent.value = ""
                    list.clear()
                    list.add("0")
                    complete.value = false
                }


                val maxLength = if (expand.value) {
                    20
                } else {
                    10
                }

                if (currentValue.length < maxLength) {
                    if (currentOperation.isNotBlank()) {
                        list.clear()
                        list.add("0")
                        currentOperation = ""
                    }

                    //处理0字符
                    currentValue = list.formatStrList()
                    if (currentValue.startsWith("0") && !currentValue.startsWith("0.") && value != ".") {
                        list.removeAt(0)
                    }
                    if (currentValue.startsWith("-0") && !currentValue.startsWith("-0.") && value != ".") {
                        list.removeAt(1)
                    }

                    if (value != "." || !currentValue.contains(".")) {
                        list.add("$value")
                    }
                }
            }

            in DataProvide.prefixSymbolList() -> {
                try {
                    val result = calculate("${value}$currentValue").stripTrailingZeros()
                    list.clear()
                    list.add(result)
                    historyComponent.value = ""
                    complete.value = true
                } catch (e: Exception) {
                    list.clear()
                    list.add("错误")
                    historyComponent.value = ""
                    operator.value = ""
                    complete.value = true
                }
            }

            in DataProvide.postfixSymbolList() -> {
                try {
                    val result = calculate("$currentValue${value}").stripTrailingZeros()
                    list.clear()
                    list.add(result)
                    historyComponent.value = ""
                    complete.value = true
                } catch (e: Exception) {
                    list.clear()
                    list.add("错误")
                    historyComponent.value = ""
                    operator.value = ""
                    complete.value = true
                }
            }

            in DataProvide.easySymbolList() -> {
                try {
                    val result = calculate(value.toString()).stripTrailingZeros()
                    list.clear()
                    list.add(result)
                    historyComponent.value = ""
                    complete.value = true
                } catch (e: Exception) {
                    list.clear()
                    list.add("错误")
                    historyComponent.value = ""
                    operator.value = ""
                    complete.value = true
                }
            }

            in DataProvide.multiInputList() -> {
                try {
                    currentOperation = value.toString()
                    if (historyComponent.value.isBlank() || highlightSymbol.value.isNotBlank()) {
                        historyComponent.value = list.formatStrList()
                        operator.value = currentOperation
                        highlightSymbol.value = currentOperation
                    } else {
                        val result = calculate("${historyComponent.value}${operator.value}$currentValue").stripTrailingZeros()
                        list.clear()
                        list.add(result)
                        historyComponent.value = result
                        operator.value = currentOperation
                        highlightSymbol.value = currentOperation
                    }
                } catch (e: Exception) {
                    list.clear()
                    list.add("错误")
                    historyComponent.value = ""
                    operator.value = ""
                }
            }

            "2nd" -> {
                secondFunction.value = !secondFunction.value
            }
            "Rad", "Deg" -> {
                degState.value = !degState.value
            }
            else -> {

            }
        }
    }

    Row(modifier = modifier.fillMaxWidth()) {
        val context = LocalContext.current
        if (expand.value) {
            CalculatorKeyboardLayout(DataProvide.generateHorizontalData(context, secondFunction.value, degState.value, onClick))
        } else {
            CalculatorKeyboardLayout(DataProvide.generateVerticalData(context, onClick))
        }
    }
}


private fun calculate(expression: String): String {
    var replaceStr = expression.replace("√", "sqrt")
        .replace("×", "*")
        .replace("÷", "/")

    val leftCount = replaceStr.count {
        it == '('
    }

    val rightCount = replaceStr.count{
        it == ')'
    }

    val count = leftCount - rightCount
    if (count > 0) {
        for (i in 0 until count) {
            replaceStr+=")"
        }
    }

    val e: Expression = ExpressionBuilder(replaceStr)
        .function(ln)
        .function(lg)
        .function(rand)
        .function(mc)
        .function(mr)
        .function(sin)
        .function(cos)
        .function(tan)
        .function(sin_1)
        .function(cos_1)
        .function(tan_1)
        .function(sinh_1)
        .function(cosh_1)
        .function(tanh_1)
        .operator(factorial)
        .operator(mPlus)
        .operator(mMinus)
        .build()
    return e.evaluate().toString()
}