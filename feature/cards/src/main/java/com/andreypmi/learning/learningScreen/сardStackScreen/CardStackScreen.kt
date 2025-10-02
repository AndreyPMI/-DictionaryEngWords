package com.andreypmi.learning.learningScreen.сardStackScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andreypmi.learning.learningScreen.viewModels.LearningSessionViewModel
import com.andreypmi.learning.learningScreen.viewModels.SessionState

@Composable
fun CardStackScreen(
    viewModel: LearningSessionViewModel,
    onSessionCompleted: () -> Unit,
    onBack: () -> Unit
) {
    val sessionState by viewModel.sessionState.collectAsState()

    LaunchedEffect(sessionState) {
        if (sessionState is SessionState.Completed) {
            onSessionCompleted()
        }
    }

    when (val state = sessionState) {
        is SessionState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is SessionState.Active -> {
            CardStackContent(
                activeState = state,
                onSwipeLeft = { word ->
                    viewModel.onWordSwiped(isKnown = false, word)
                },
                onSwipeRight = { word ->
                    viewModel.onWordSwiped(isKnown = true, word)
                },
                onCardFlip = { cardId ->
                    viewModel.flipCard(cardId)
                },
                onBack = onBack
            )
        }
        is SessionState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Ошибка: ${state.message}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBack) { Text("Назад") }
                }
            }
        }
        else -> {
            onBack()
        }
    }
}