package com.example.tasksapp.di

import android.app.Application
import androidx.room.Room
import com.example.tasksapp.data.TaskDatabase
import com.example.tasksapp.data.TaskRepository
import com.example.tasksapp.data.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            "task_table"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(db.dao)
    }
}