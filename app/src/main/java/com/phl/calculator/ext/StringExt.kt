package com.phl.calculator.ext

import androidx.compose.runtime.snapshots.SnapshotStateList
import java.math.BigDecimal


fun String.stripTrailingZeros(): String {
    if (this == "NaN") {
        return this
    }

    return BigDecimal(this).stripTrailingZeros().toPlainString()
}


fun SnapshotStateList<String>.formatStrList(): String{
    if (this.isEmpty() ) {
        return ""
    }
    val builder = StringBuilder("")
    for (str in this) {
        builder.append(str)
    }
    return builder.toString()
}