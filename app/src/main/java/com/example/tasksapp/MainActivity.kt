package com.example.tasksapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tasksapp.ui.single_task.AddEditTodoScreen
import com.example.tasksapp.ui.theme.MVVMTodoAppTheme
import com.example.tasksapp.ui.task_list.TaskListScreen
import com.example.tasksapp.utilits.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMTodoAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.TASK_LIST
                ) {
                    composable(Routes.TASK_LIST) {
                        TaskListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.SINGLE_TASK + "?taskId={taskId}",
                        arguments = listOf(
                            navArgument(name = "taskId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditTodoScreen(OnBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}