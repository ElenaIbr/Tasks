package com.example.tasksapp.ui.task_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksapp.data.Task
import com.example.tasksapp.data.TaskRepository
import com.example.tasksapp.utilits.Routes
import com.example.tasksapp.utilits.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {

    var tasks = repository.getTasks()
    var dailyTasks = repository.getDailyTasks(true)

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTask: Task? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: TaskListEvent) {
        when(event) {
            is TaskListEvent.OnTaskClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.SINGLE_TASK + "?taskId=${event.task.id}"))
            }
            is TaskListEvent.OnAddTaskClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.SINGLE_TASK))
            }
            is TaskListEvent.OnUndoDeleteClick -> {
                deletedTask?.let { task ->
                    viewModelScope.launch {
                        repository.insertTask(task)
                    }
                }
            }
            is TaskListEvent.OnDeleteTaskClick -> {
                viewModelScope.launch {
                    deletedTask = event.task
                    repository.deleteTask(event.task)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Task was deleted",
                        action = "Return"
                    ))
                }
            }
            is TaskListEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    repository.insertTask(
                        event.task.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is TaskListEvent.OnDailyTaskUpdate -> {
                viewModelScope.launch {
                    val delta = Calendar.getInstance().timeInMillis - event.task.time
                    if (TimeUnit.MILLISECONDS.toDays(delta) > 0) {
                        repository.insertTask(
                            event.task.copy(
                                time = Calendar.getInstance().timeInMillis,
                                isDone = false
                            )
                        )
                    }
                }
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}