package com.andreypmi.learning.learningScreen.сardStackScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.SwipeableState
import androidx.wear.compose.material.swipeable
import com.andreypmi.core_domain.models.Word
import com.andreypmi.learning.learningScreen.viewModels.SessionState
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun CardStackContent(
    activeState: SessionState.Active,
    onSwipeLeft: (Word) -> Unit,
    onSwipeRight: (Word) -> Unit,
    onCardFlip: (Int) -> Unit,
    onBack: () -> Unit
) {
    val currentCard = activeState.words.getOrNull(activeState.currentIndex) ?: return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Фоновые карточки
        val startIndex = activeState.currentIndex + 1
        if (activeState.words.size > startIndex) {
            val remainingCards = activeState.words.drop(startIndex).take(3)
            remainingCards.reversed().forEachIndexed { index, card ->
                CardItem(
                    word = card,
                    isFlipped = card.id in activeState.flippedCardIds,
                    onFlip = { },
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .align(Alignment.Center)
                        .offset(
                            x = (index + 1) * 4.dp * (if (index % 2 == 0) 1 else -1),
                            y = -(index + 1) * 6.dp
                        )
                        .graphicsLayer {
                            scaleX = 1f - (index + 1) * 0.03f
                            scaleY = 1f - (index + 1) * 0.03f
                            rotationZ = (index + 1) * 2f * (if (index % 2 == 0) 1 else -1)
                        }
                )
            }
        }

        val swipeableState by remember(activeState.currentIndex) {
            derivedStateOf { SwipeableState(0) }
        }

        val cardSize = 300.dp

        val swipeAnchors = mapOf(
            0f to 0,
            -cardSize.value to -1,
            cardSize.value to 1
        )

        LaunchedEffect(swipeableState.currentValue) {
            if (swipeableState.currentValue != 0) {
                delay(300)
                when (swipeableState.currentValue) {
                    -1 -> onSwipeLeft(currentCard)
                    1 -> onSwipeRight(currentCard)
                }
            }
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + scaleIn(),
            modifier = Modifier
                .size(cardSize, 200.dp)
                .align(Alignment.Center)
        ) {
            CardItem(
                word = currentCard,
                isFlipped = currentCard.id in activeState.flippedCardIds,
                onFlip = { currentCard.id?.let { onCardFlip(it) } },
                modifier = Modifier
                    .fillMaxSize()
                    .swipeable(
                        state = swipeableState,
                        anchors = swipeAnchors,
                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                        orientation = Orientation.Horizontal
                    )
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .graphicsLayer {
                        alpha = 1f - (abs(swipeableState.offset.value) / cardSize.value)
                        rotationZ = swipeableState.offset.value * 0.1f
                    }
            )
        }

        val progress = if (activeState.words.isEmpty()) 0f
        else activeState.currentIndex.toFloat() / activeState.words.size

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