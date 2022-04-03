package com.example.tasksapp.ui.single_task

sealed class SingleTaskEvent {
    data class OnTitleChange(val title: String): SingleTaskEvent()
    data class OnDescriptionChange(val description: String): SingleTaskEvent()
    data class OnDailyStatusChange(val taskForToday: Boolean): SingleTaskEvent()
    data class OnColorChange(val color: Int): SingleTaskEvent()
    object OnSaveTaskClick: SingleTaskEvent()
}
