package com.andreypmi.dictionaryforwords.word_list.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andreypmi.dictionaryforwords.core.ui.theme.dimension

@Composable
fun CategoryBookmark(
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
   
    Box(
        modifier = modifier
            .width(MaterialTheme.dimension.size32)
            .height(MaterialTheme.dimension.size48)
            .clip(RoundedCornerShape(topStart = MaterialTheme.dimension.size8, bottomStart = MaterialTheme.dimension.size8))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(topStart = MaterialTheme.dimension.size8, bottomStart = MaterialTheme.dimension.size8)
            )
            .clickable(onClick = onToggle),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowRight else Icons.Default.KeyboardArrowLeft,
            contentDescription = "Категории",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(MaterialTheme.dimension.size24)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview(){
    CategoryBookmark(
        isExpanded = false,
        onToggle = {},
        modifier = Modifier
    )
}
