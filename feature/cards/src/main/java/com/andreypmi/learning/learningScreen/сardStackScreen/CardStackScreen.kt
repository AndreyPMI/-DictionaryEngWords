package com.andreypmi.learning.learningScreen.сardStackScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.Word
import com.andreypmi.learning.learningScreen.viewModels.SessionState

@Composable
fun CardStackScreen(
    sessionState: SessionState,
    onSwipeLeft: (Word) -> Unit,
    onSwipeRight: (Word) -> Unit,
    onCardFlip: (Int) -> Unit,
    onSessionCompleted: () -> Unit,
    onBack: () -> Unit
) {
    var hasHandledCompletion by remember { mutableStateOf(false) }
    LaunchedEffect(sessionState) {
        if (sessionState is SessionState.Active) {
            hasHandledCompletion = false
        }
    }
    when (sessionState) {
        is SessionState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is SessionState.Active -> {

            CardStackContent(
                activeState = sessionState,
                onSwipeLeft = onSwipeLeft,
                onSwipeRight = onSwipeRight,
                onCardFlip = onCardFlip,
                onBack = onBack
            )
        }

        is SessionState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Ошибка: ${sessionState.message}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBack) { Text("Назад") }
                }
            }
        }

        SessionState.Completed -> {
            LaunchedEffect(Unit) {
                if (!hasHandledCompletion) {
                    hasHandledCompletion = true
                    onSessionCompleted()
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        SessionState.Idle -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Сессия не начата")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBack) { Text("Назад") }
                }
            }
        }

        is SessionState.EmptyCategory -> {
            EmptyCategoryState(onBack = onBack)
        }
        is SessionState.SessionFinished -> {
            AnimatedVisibility(
                visible = true,
                exit = fadeOut() + shrinkOut()
            ) {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun EmptyCategoryState(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "В этой группе нет слов",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Добавьте слова в группу чтобы начать тренировку",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(onClick = onBack) {
                Text("Вернуться к категориям")
            }
        }
    }
}