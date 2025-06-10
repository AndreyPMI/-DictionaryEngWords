package com.andreypmi.dictionaryforwords.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalNewsAggregatorDimension = staticCompositionLocalOf<Dimensions> {
    error("No dimension provided")
}

data class Dimensions(
    val size4: Dp = 4.dp,
    val size8: Dp = 8.dp,
    val size16: Dp = 16.dp,
    val size24: Dp = 24.dp,
    val size32: Dp = 32.dp,
    val size48: Dp = 48.dp,
    val size64: Dp = 64.dp,
    val size72: Dp = 72.dp,
)