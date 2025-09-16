package com.andreypmi.learning.сardStackScreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun CardStackScreen(
    categoryId: String,
    onSessionFinished: (Any?)-> Unit,
    onBack: ()-> Unit

) {
    Button(onClick = onBack) {
        Text("CardStackScreen")
    }
}