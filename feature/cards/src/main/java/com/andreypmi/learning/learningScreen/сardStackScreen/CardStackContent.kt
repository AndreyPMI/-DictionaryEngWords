package com.andreypmi.learning.learningScreen.сardStackScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.andreypmi.core_domain.models.Word
import com.andreypmi.learning.learningScreen.viewModels.SessionState
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun CardStackContent(
    activeState: SessionState.Active,
    onSwipeLeft: (Word) -> Unit,
    onSwipeRight: (Word) -> Unit,
    onCardFlip: (Int) -> Unit,
    onBack: () -> Unit
) {
    val currentCardIndex = activeState.currentIndex
    var backgroundColor by remember { mutableStateOf(Transparent) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        val visibleCards = activeState.words.drop(currentCardIndex).take(4)

        visibleCards.forEachIndexed { index, card ->
            val isTopCard = index == 0
            val layerIndex = visibleCards.size - 1 - index

            CardItem(
                word = card,
                isFlipped = card.id in activeState.flippedCardIds,
                onFlip = {
                    card.id?.let { onCardFlip(it) }
                },
                modifier = Modifier
                    .size(300.dp, 200.dp)
                    .align(Alignment.Center)
                    .offset(
                        x = layerIndex * 4.dp * (if (layerIndex % 2 == 0) 1 else -1),
                        y = -layerIndex * 6.dp
                    )
                    .graphicsLayer {
                        scaleX = 1f - layerIndex * 0.03f
                        scaleY = 1f - layerIndex * 0.03f
                        rotationY = layerIndex * 0.5f * (if (layerIndex % 2 == 0) 1 else -1)
                    }
                    .zIndex(layerIndex.toFloat())
                    .let { modifier ->
                        if (isTopCard) {
                            modifier.swipeableCard(
                                currentCard = card,
                                onSwipeLeft = onSwipeLeft,
                                onSwipeRight = onSwipeRight,
                                onSwipeStateChange = { offsetX ->
                                    backgroundColor = when {
                                        offsetX > 0 -> Green.copy(alpha = 0.2f)
                                        offsetX < 0 -> Red.copy(alpha = 0.2f)
                                        else -> Transparent
                                    }
                                }
                            )
                        } else {
                            modifier.pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        awaitPointerEvent()
                                    }
                                }
                            }
                        }
                    }
            )
        }

        val progress = if (activeState.words.isEmpty()) 0f
        else currentCardIndex.toFloat() / activeState.words.size

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 32.dp, vertical = 16.dp),
        )

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
    }
}

@Composable
private fun Modifier.swipeableCard(
    currentCard: Word,
    onSwipeLeft: (Word) -> Unit,
    onSwipeRight: (Word) -> Unit,
    onSwipeStateChange: (Float) -> Unit
): Modifier {
    var offsetX by remember { mutableStateOf(0f) }
    var isSwiped by remember { mutableStateOf(false) }

    LaunchedEffect(offsetX) {
        onSwipeStateChange(offsetX)
    }

    LaunchedEffect(isSwiped) {
        if (isSwiped) {
            if (offsetX > 0) {
                onSwipeRight(currentCard)
            } else {
                onSwipeLeft(currentCard)
            }
            delay(100)
            offsetX = 0f
            isSwiped = false
            onSwipeStateChange(0f)
        }
    }

    return this
        .pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, dragAmount ->
                    if (abs(offsetX + dragAmount.x) > 0) {
                        change.consume()
                        offsetX += dragAmount.x
                    }
                },
                onDragEnd = {
                    if (abs(offsetX) > 100f) {
                        isSwiped = true
                    } else {
                        offsetX = 0f
                        onSwipeStateChange(0f)
                    }
                }
            )
        }
        .offset { IntOffset(offsetX.roundToInt(), 0) }
}