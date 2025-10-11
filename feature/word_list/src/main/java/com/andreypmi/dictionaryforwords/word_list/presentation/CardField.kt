package com.andreypmi.dictionaryforwords.word_list.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordState

@Composable
internal fun CardField(
    word: WordState,
    modifier: Modifier = Modifier,
    onClickCard: () -> Unit = {},
    onDeleteClicked: (WordState) -> Unit = {},
    onEditClicked: (WordState) -> Unit = {}
) {
    val isDescriptionVisible by remember { mutableStateOf<Boolean>(false) } //TODO add logic for change
    var expanded by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(DpOffset.Zero) }
    val density = LocalDensity.current
    val animatedScale by animateFloatAsState(
        targetValue = if (expanded) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150), label = ""
    )
    var isTranslated by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .scale(animatedScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        expanded = true
                        offset = with(density) {
                            DpOffset(it.x.toDp() - 32.dp, it.y.toDp() - 64.dp)
                        }
                    },
                    onTap = { isTranslated = !isTranslated }
                )
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if(isTranslated == true){
            Text(
                text = word.word,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                Text(
                    modifier = if (isDescriptionVisible) Modifier.blur(10.dp) else Modifier,
                    text = word.description,
                    style = MaterialTheme.typography.bodySmall,
                )
            }}
            else{
                Text(
                    text = word.translation,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            offset = offset,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.edit)) },
                onClick = {
                    onEditClicked(word)
                    expanded = false
                }
            )
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.delete)) },
                onClick = {
                    expanded = false
                    onDeleteClicked(word)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
private fun Preview() {
    CardField(WordState("1", "1", "ruWord", "Descr", "des"))
}