package com.example.tasksapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Int): Task?

    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE taskForToday == :isToday")
    fun getDailyTasks(isToday: Boolean): Flow<List<Task>>
}
