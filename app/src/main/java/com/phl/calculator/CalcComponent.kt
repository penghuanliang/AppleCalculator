package com.phl.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.phl.calculator.data.ButtonData

@Composable
fun CalculatorHorizontalLayout(
    list: List<List<ButtonData>>,
) {

    Column {
        list.forEach {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                it.forEach {
                    CalculatorButton(
                        text = it.text,
                        width = it.width,
                        height = it.height,
                        textColor = it.textColor,
                        bgColor = it.bgColor,
                        onClick = it.onClick
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorButton(
    text: String,
    width: Int,
    height: Int,
    textColor: Color,
    bgColor: Color,
    onClick: (value: Any) -> Unit
) {
    val textSize = with(LocalDensity.current) { height.toSp() }
    val padding = with(LocalDensity.current) { height.toDp() }

    Card(
        modifier = Modifier
            .size(width.dp, height.dp)
            .padding(padding / 8),
        elevation = CardDefaults.cardElevation(3.dp),
        containerColor = Color.Transparent
    ) {
        Box(
            contentAlignment = if (text == "0") Alignment.CenterStart else Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(text)
                }
                .background(
                    color = bgColor,
                    shape = CircleShape
                )
        ) {

            val textModifier = if (text == "0") {
                Modifier.width((width/2).dp - padding / 3)
            } else {
                Modifier.fillMaxWidth()
            }

            Text(
                text = text,
                color = textColor,
                fontSize = textSize,
                textAlign = TextAlign.Center,
                modifier = textModifier
            )
        }
    }
}