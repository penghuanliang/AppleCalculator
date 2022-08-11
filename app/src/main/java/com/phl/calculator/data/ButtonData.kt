package com.phl.calculator.data

import androidx.compose.ui.graphics.Color

data class ButtonData(
    val text: String,
    val width: Int,
    val height: Int = width,
    val textColor: Color,
    val bgColor: Color,
    val onClick: (value: Any) -> Unit
)