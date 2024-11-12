package com.andreypmi.dictionaryforwords.presentation.features

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun CardField(
    word : com.andreypmi.dictionaryforwords.domain.models.Word,
    onClickCard: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isDescriptionVisible by remember { mutableStateOf<Boolean>(false) } //TODO add logic for change
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClickCard),
        shape = RoundedCornerShape(8.dp),
       elevation =  CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
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
        }
    }
}}

@Composable
@Preview(showBackground = true, showSystemUi = false)
private fun Preview(){
    CardField(com.andreypmi.dictionaryforwords.domain.models.Word(1, "ruWord", "Descr","des"))
}