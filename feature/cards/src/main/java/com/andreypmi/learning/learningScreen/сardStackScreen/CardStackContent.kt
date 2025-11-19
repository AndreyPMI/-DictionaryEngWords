package com.andreypmi.learning.learningScreen.сardStackScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.andreypmi.cards.R
import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.core.ui.R as Rui
import com.andreypmi.learning.learningScreen.viewModels.SessionState
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun CardStackContent(
    activeState: SessionState.Active,
    onSwipeLeft: (Word) -> Unit,
    onSwipeRight: (Word) -> Unit,
    onCardFlip: (String) -> Unit,
    onBack: () -> Unit
) {
    val currentCardIndex = activeState.currentIndex
    var backgroundColor by remember { mutableStateOf(Transparent) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(painter = painterResource(Rui.drawable.arrow_back_24dp), contentDescription = "Back")
            }

            val progress = if (activeState.words.isEmpty()) 0f
            else activeState.currentIndex.toFloat() / activeState.words.size

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            val visibleCards = activeState.words.drop(currentCardIndex).take(4)
            visibleCards.forEachIndexed { index, card ->
                val isTopCard = index == 0
                val layerIndex = visibleCards.size - 1 - index
                key(card.id) {
                    CardItem(
                        word = card,
                        isFlipped = card.id in activeState.flippedCardIds,
                        isFlipActive = isTopCard,
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
            }
        }
        SwipeHints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 24.dp)
        )

    }
}

@Composable
private fun SwipeHints(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Rui.drawable.keyboard_arrow_left_24dp),
                contentDescription = "Swipe left",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.forgot),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Rui.drawable.keyboard_arrow_right_24dp),
                contentDescription = "Swipe right",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.remember),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
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