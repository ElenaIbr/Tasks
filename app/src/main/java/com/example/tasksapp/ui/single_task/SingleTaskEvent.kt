package com.example.tasksapp.ui.single_task

import androidx.compose.ui.graphics.Color

sealed class SingleTaskEvent {
    data class OnTitleChange(val title: String): SingleTaskEvent()
    data class OnDescriptionChange(val description: String): SingleTaskEvent()
    data class OnDailyStatusChange(val taskForToday: Boolean): SingleTaskEvent()
    data class OnColorChange(val color: Map.Entry<Color, Color>): SingleTaskEvent()
    object OnSaveTaskClick: SingleTaskEvent()
}
