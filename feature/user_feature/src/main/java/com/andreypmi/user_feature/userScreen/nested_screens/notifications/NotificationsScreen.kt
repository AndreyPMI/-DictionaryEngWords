package com.andreypmi.user_feature.userScreen.nested_screens.notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.settingsModel.NotificationContentType
import com.andreypmi.core_domain.models.settingsModel.NotificationInterval
import com.andreypmi.core_domain.models.settingsModel.NotificationSettings
import com.andreypmi.user_feature.userScreen.nested_screens.notifications.models.NotificationsEvent
import com.andreypmi.user_feature.userScreen.nested_screens.notifications.models.NotificationsState
import com.andreypmi.user_feature.userScreen.nested_screens.notifications.models.TimePickerType
import com.andreypmi.core_domain.models.settingsModel.TimeRange
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.andreypmi.dictionaryforwords.core.ui.R as Rui

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    state: NotificationsState,
    onEvent: (NotificationsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Уведомления") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(NotificationsEvent.NavigateBack) }) {
                        Icon(painterResource(Rui.drawable.ic_person), contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> LoadingState()
            else -> NotificationSettingsContent(
                state = state,
                onEvent = onEvent,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    when (state.showTimePicker) {
        is TimePickerType.StartTime -> {
            TimePickerDialog(
                initialTime = state.settings.timeRange.startTime,
                onTimeSelected = { hour, minute ->
                    onEvent(
                        NotificationsEvent.SetTimeRange(
                            state.settings.timeRange.copy(
                                startHour = hour,
                                startMinute = minute
                            )
                        )
                    )
                    onEvent(NotificationsEvent.HideTimePicker)
                },
                onDismiss = { onEvent(NotificationsEvent.HideTimePicker) }
            )
        }

        is TimePickerType.EndTime -> {
            TimePickerDialog(
                initialTime = state.settings.timeRange.endTime,
                onTimeSelected = { hour, minute ->
                    onEvent(
                        NotificationsEvent.SetTimeRange(
                            state.settings.timeRange.copy(
                                endHour = hour,
                                endMinute = minute
                            )
                        )
                    )
                    onEvent(NotificationsEvent.HideTimePicker)
                },
                onDismiss = { onEvent(NotificationsEvent.HideTimePicker) }
            )
        }

        null -> Unit
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NotificationSettingsContent(
    state: NotificationsState,
    onEvent: (NotificationsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SwitchSettingItem(
            title = "Включить уведомления",
            subtitle = "Регулярно получайте новые слова для изучения",
            checked = state.isEnabled,
            onCheckedChange = { enabled ->
                onEvent(NotificationsEvent.SetEnabled(enabled))
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isEnabled) {
            NotificationSettingsOptions(
                settings = state.settings,
                onEvent = onEvent
            )
        } else {
            FeatureDescription()
        }
    }
}

@Composable
fun FeatureDescription() {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Уведомления помогут:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Регулярно повторять слова")
            Text("• Увеличивать словарный запас")
            Text("• Не забывать об обучении")
        }
    }
}

@Composable
fun NotificationSettingsOptions(
    settings: NotificationSettings,
    onEvent: (NotificationsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingSection(title = "Как часто?") {
            IntervalSelection(
                selectedInterval = settings.interval,
                onIntervalSelected = { interval ->
                    onEvent(NotificationsEvent.SetInterval(interval))
                }
            )
        }

        SettingSection(title = "В какое время?") {
            TimeRangeSelection(
                timeRange = settings.timeRange,
                onStartTimeClick = { onEvent(NotificationsEvent.ShowStartTimePicker) },
                onEndTimeClick = { onEvent(NotificationsEvent.ShowEndTimePicker) }
            )
        }

        SettingSection(title = "В какие дни?") {
            DaysOfWeekSelection(
                selectedDays = settings.daysOfWeek,
                onDaysChanged = { days ->
                    onEvent(NotificationsEvent.SetDaysOfWeek(days))
                }
            )
        }

        SettingSection(title = "Что показывать?") {
            ContentTypeSelection(
                selectedType = settings.contentType,
                onTypeSelected = { type ->
                    onEvent(NotificationsEvent.SetContentType(type))
                }
            )
        }

        NotificationPreview(settings = settings)
    }
}

@Composable
fun IntervalSelection(
    selectedInterval: NotificationInterval,
    onIntervalSelected: (NotificationInterval) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        NotificationInterval.entries.forEach { interval ->
            RadioButtonItem(
                text = when (interval) {
                    NotificationInterval.HOURS_1 -> "Каждый час"
                    NotificationInterval.HOURS_2 -> "Каждые 2 часа"
                    NotificationInterval.HOURS_5 -> "Каждые 5 часов"
                },
                selected = selectedInterval == interval,
                onSelected = { onIntervalSelected(interval) }
            )
        }
    }
}

@Composable
fun TimeRangeSelection(
    timeRange: TimeRange,
    onStartTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TimeSelectionItem(
            label = "Начинать с",
            time = timeRange.startTime,
            onClick = onStartTimeClick
        )
        Spacer(modifier = Modifier.height(8.dp))
        TimeSelectionItem(
            label = "Заканчивать в",
            time = timeRange.endTime,
            onClick = onEndTimeClick
        )
    }
}

@Composable
fun DaysOfWeekSelection(
    selectedDays: Set<DayOfWeek>,
    onDaysChanged: (Set<DayOfWeek>) -> Unit,
    modifier: Modifier = Modifier
) {
    val days = listOf(
        "Пн" to DayOfWeek.MONDAY,
        "Вт" to DayOfWeek.TUESDAY,
        "Ср" to DayOfWeek.WEDNESDAY,
        "Чт" to DayOfWeek.THURSDAY,
        "Пт" to DayOfWeek.FRIDAY,
        "Сб" to DayOfWeek.SATURDAY,
        "Вс" to DayOfWeek.SUNDAY
    )

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        days.forEach { (label, day) ->
            DayChip(
                label = label,
                selected = selectedDays.contains(day),
                onSelected = { selected ->
                    val newDays = if (selected) {
                        selectedDays + day
                    } else {
                        selectedDays - day
                    }
                    onDaysChanged(newDays)
                }
            )
        }
    }
}

@Composable
fun ContentTypeSelection(
    selectedType: NotificationContentType,
    onTypeSelected: (NotificationContentType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        NotificationContentType.entries.forEach { type ->
            RadioButtonItem(
                text = when (type) {
                    NotificationContentType.WORD_TRANSLATION -> "Слово + перевод"
                    NotificationContentType.WORD_EXAMPLE -> "Слово + пример использования"
                    NotificationContentType.PHRASE_OF_DAY -> "Фраза дня"
                    NotificationContentType.MINI_QUIZ -> "Мини-тест"
                },
                selected = selectedType == type,
                onSelected = { onTypeSelected(type) }
            )
        }
    }
}

@Composable
fun SwitchSettingItem(
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
fun TimeSelectionItem(
    label: String,
    time: LocalTime,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = DateTimeFormatter.ofPattern("HH:mm").format(time),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RadioButtonItem(
    text: String,
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun DayChip(
    label: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onSelected(!selected) }
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun NotificationPreview(
    settings: NotificationSettings,
    modifier: Modifier = Modifier
) {
    SettingSection(title = "Предпросмотр") {
        Card(
            modifier = modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Пример уведомления:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (settings.contentType) {
                        NotificationContentType.WORD_TRANSLATION -> "Hello - Привет"
                        NotificationContentType.WORD_EXAMPLE -> "Book - I'm reading a book"
                        NotificationContentType.PHRASE_OF_DAY -> "Break a leg! - Ни пуха ни пера!"
                        NotificationContentType.MINI_QUIZ -> "Выбери перевод: Beautiful"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun TimePickerDialog(
    initialTime: LocalTime,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    // TODO: Реализовать кастомный Time Picker
}