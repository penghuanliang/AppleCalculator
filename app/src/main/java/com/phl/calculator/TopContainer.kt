package com.phl.calculator

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.phl.calculator.ext.formatStrList

@Composable
fun TopTextContainer(
    modifier: Modifier = Modifier,
    expand: MutableState<Boolean>,
    list: SnapshotStateList<String>,
    error: MutableState<Boolean>
) {
    val fontSize = if (expand.value) 20 else 50
    val context = LocalContext.current
    val width = context.resources.configuration.screenWidthDp
    val requireSize = if (expand.value) width / 10 else width / 4

    val padding = with(LocalDensity.current) {
        ((requireSize - fontSize) / 2).toDp() + requireSize.toDp() / 2
    }


    SelectionContainer(modifier = modifier) {
        TextField(
            value = list.formatStrList(),
            onValueChange = { },
            maxLines = 1,
            textStyle = TextStyle(
                color = if (error.value) {
                    Color.Red
                } else {
                    Color.White
                },
                fontSize = fontSize.sp,
                textAlign = TextAlign.End,
            ),
            readOnly = true,
            isError = error.value,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.None
            ),

            shape = RectangleShape,
            modifier = Modifier
                .padding(start = padding, end = padding)
                .wrapContentHeight(Alignment.Bottom)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}