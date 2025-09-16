package com.andreypmi.learning.sessionResultScreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SessionResultScreen(
    categoryId : String,
    difficultWordIds : List<String>,
    allWordIds : List<String>,
    onRetry : (Any?)-> Unit,
    onFinish: ()-> Unit,
) {
    Button(onClick = onFinish) {
        Text("SessionResultScreen")
    }
}