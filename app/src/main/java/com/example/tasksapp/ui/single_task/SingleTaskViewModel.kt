package com.example.tasksapp.ui.single_task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksapp.data.Task
import com.example.tasksapp.data.TaskRepository
import com.example.tasksapp.ui.theme.PinkBackgroundColor
import com.example.tasksapp.utilits.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SingleTaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var task by mutableStateOf<Task?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var taskForToday by mutableStateOf(false)
        private set

    var color by mutableStateOf(PinkBackgroundColor.toArgb())
        private set

    var buttonIsEnabled by mutableStateOf(false)
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val taskId = savedStateHandle.get<Int>("taskId")!!
        if(taskId != -1) {
            viewModelScope.launch {
                repository.getTaskById(taskId)?.let { task ->
                    title = task.title
                    description = task.description ?: ""
                    taskForToday = task.taskForToday
                    color = task.color
                    this@SingleTaskViewModel.task = task
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: SingleTaskEvent) {
        when(event) {
            is SingleTaskEvent.OnTitleChange -> {
                title = event.title
            }
            is SingleTaskEvent.OnDescriptionChange -> {
                description = event.description
            }
            is SingleTaskEvent.OnSaveTaskClick -> {
                viewModelScope.launch {
                    repository.insertTask(
                        Task(
                            title = title,
                            description = description,
                            isDone = task?.isDone ?: false,
                            taskForToday = taskForToday,
                            time = Calendar.getInstance().timeInMillis,
                            color = color,
                            id = task?.id
                        )
                    )
                    sendUiEvent(UiEvent.OnBackStack)
                }
            }
            is SingleTaskEvent.OnDailyStatusChange -> {
                taskForToday = event.taskForToday
            }
            is SingleTaskEvent.OnColorChange -> {
                color = event.color
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}