package com.andreypmi.user_feature.userScreen.nested_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun UserMainScreen(
    onShareGroupClick: () -> Unit,
    onLoadGroupClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Обмен словарями",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )

                Button(
                    onClick = onShareGroupClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(com.andreypmi.dictionaryforwords.core.ui.R.drawable.ic_book),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Поделиться группой")
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = onLoadGroupClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(com.andreypmi.dictionaryforwords.core.ui.R.drawable.ic_book),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Загрузить группу")
                }

                Spacer(Modifier.height(16.dp))
            }
        }

        UserMenuItem(
            icon = com.andreypmi.dictionaryforwords.core.ui.R.drawable.ic_card,
            title = "Уведомления",
            onClick = onNotificationsClick
        )

        UserMenuItem(
            icon = com.andreypmi.dictionaryforwords.core.ui.R.drawable.ic_card,
            title = "Настройки",
            onClick = onSettingsClick
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun UserMenuItem(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}