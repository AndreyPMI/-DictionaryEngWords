package com.andreypmi.learning.learningScreen.сardStackScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.Word

@Composable
fun CardItem(
    word: Word,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation = animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    LaunchedEffect(isFlipped) {
        println("🔄 CARDITEM: word=${word.word}, id=${word.id}, isFlipped=$isFlipped")
    }
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onFlip() })
            }
    ) {
        Card(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationY = rotation.value
                    alpha = if (rotation.value < 90f) 1f else 0f
                    cameraDistance = 8f * density
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = word.word,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Flip card",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(24.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationY = rotation.value + 180f
                    alpha = if (rotation.value > 90f) 1f else 0f
                    cameraDistance = 8f * density
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = word.translate,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

            }
        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun preview(){
    CardItem(
        word = Word(
            id = 1,
            idCategory = 2,
            word = "Word",
            translate = "Translate",
            description = "Description"
        ),
        isFlipped = false,
        onFlip = {},
    )
}