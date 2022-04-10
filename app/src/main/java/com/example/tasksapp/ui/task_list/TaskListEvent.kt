package com.example.tasksapp.ui.task_list

import com.example.tasksapp.data.Task

sealed class TaskListEvent {
    data class OnDeleteTaskClick(val task: Task): TaskListEvent()
    data class OnCheckedChange(val task: Task, val isDone: Boolean): TaskListEvent()
    object OnUndoDeleteClick: TaskListEvent()
    data class OnTaskClick(val task: Task): TaskListEvent()
    object OnAddTaskClick: TaskListEvent()
    data class OnDailyTaskUpdate(val task: Task): TaskListEvent()
}
