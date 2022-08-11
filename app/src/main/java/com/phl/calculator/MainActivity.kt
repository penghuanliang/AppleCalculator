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

    private val saveValueState = mutableStateOf("")
    private val currentValueState = mutableStateListOf("0")
    private val operatorState = mutableStateOf("")
    private val errorState = mutableStateOf(false)
    private val orientationState by lazy { mutableStateOf(resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(saveValueState, currentValueState, operatorState, errorState, orientationState)
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
    errorState: MutableState<Boolean>,
    orientationState: MutableState<Boolean>
) {
    val historyComponent = remember { saveValueState }
    val formulaComponent = remember { currentValueState }
    val operator = remember { operatorState }
    val onError = remember { errorState }
    val expand = remember { orientationState }


    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            TopTextContainer(
                expand = expand,
                list = formulaComponent,
                error = onError
            )

            NumPad(
                modifier = Modifier.padding(bottom = 10.dp),
                expand = expand,
                historyComponent = historyComponent,
                list = formulaComponent,
                operator = operator,
                onError = onError
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
     val errorState = remember {
         mutableStateOf(false)
     }
    CalculatorTheme {
        Greeting(saveValueState, currentValueState, operatorState, errorState, orientationState)
    }
}

@Composable
fun NumPad(
    modifier: Modifier = Modifier,
    expand: MutableState<Boolean>,
    historyComponent: MutableState<String>,
    list: SnapshotStateList<String>,
    operator: MutableState<String>,
    onError: MutableState<Boolean>
) {
    var currentOperation = ""
    val onClick: (value: Any) -> Unit = { value: Any ->
        var currentValue = list.formatStrList()

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

            "+", "-", "×", "÷" -> {
                try {
                    currentOperation = value.toString()
                    operator.value = currentOperation

                    if (historyComponent.value.isBlank()) {
                        historyComponent.value = list.formatStrList()
                    } else {
                        val result =
                            calculate("${historyComponent.value}$value$currentValue").stripTrailingZeros()
                        if (result == "NaN") {
                            onError.value = true
                        }
                        list.clear()
                        list.add(result)
                        historyComponent.value = result
                    }
                } catch (e: Exception) {
                    onError.value = true
                    list.clear()
                    list.add("Error!")
                }
            }

            "%" -> {
                try {
                    val result =
                        BigDecimal(currentValue).divide(BigDecimal(100)).stripTrailingZeros()
                            .toString()
                    if (result == "NaN") {
                        onError.value = true
                    }
                    list.clear()
                    list.add(result)
                    historyComponent.value = ""
                } catch (e: Exception) {
                    onError.value = true
                    list.clear()
                    list.add("Error!")
                }
            }

            "=" -> {
                try {
                    val result =
                        calculate("${historyComponent.value}${operator.value}$currentValue").stripTrailingZeros()
                    if (result == "NaN") {
                        onError.value = true
                    }
                    list.clear()
                    list.add(result)
                    historyComponent.value = ""
                } catch (e: Exception) {
                    onError.value = true
                    list.clear()
                    list.add("Error!")
                }
            }
            "AC" -> {
                list.clear()
                list.add("0")
                historyComponent.value = ""
            }

            in DataProvide.intList() -> {
                if (onError.value) {
                    list.clear()
                    list.add("$value")
                    onError.value = false
                } else {
                    if (currentValue.length < 10) {
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

                        list.add("$value")
                    }
                }
            }

            //展开的运算符待完善
            else -> {

            }
        }
    }

    Row(modifier = modifier.fillMaxWidth()) {
        val context = LocalContext.current
        if (expand.value) {
            CalculatorHorizontalLayout(DataProvide.generateHorizontalData(context, onClick))
        } else {
            CalculatorHorizontalLayout(DataProvide.generateVerticalData(context, onClick))
        }
    }
}


private fun calculate(expression: String): String {
    val replaceStr = expression.replace("√", "sqrt")
        .replace("×", "*")
        .replace("÷", "/")

    val e: Expression = ExpressionBuilder(replaceStr)
        .function(ln)
        .function(lg)
        .operator(factorial)
        .build()
    return e.evaluate().toString()
}