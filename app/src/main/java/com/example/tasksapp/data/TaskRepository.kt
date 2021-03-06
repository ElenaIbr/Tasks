package com.example.tasksapp.data

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun getTaskById(id: Int): Task?

    fun getTasks(): Flow<List<Task>>

    fun getDailyTasks(isToday: Boolean): Flow<List<Task>>
}