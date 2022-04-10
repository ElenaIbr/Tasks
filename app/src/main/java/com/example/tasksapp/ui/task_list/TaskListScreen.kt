package com.example.tasksapp.ui.task_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tasksapp.R
import com.example.tasksapp.ui.theme.AccentColor
import com.example.tasksapp.ui.theme.WhiteSmoke
import com.example.tasksapp.utilits.UiEvent
import kotlinx.coroutines.flow.collect

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())
    val dailyTasks = viewModel.dailyTasks.collectAsState(initial = emptyList())

    val scaffoldState = rememberScaffoldState()

    val list = remember {
        mutableStateOf(tasks)
    }

    var tabIndex by remember { mutableStateOf(0) }
    val tabData = listOf(
        stringResource(id = R.string.all_tasks),
        stringResource(id = R.string.daily_tasks)
    )

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TaskListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = AccentColor,
                contentColor = Color.Black,
                shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 50)),
                onClick = { viewModel.onEvent(TaskListEvent.OnAddTaskClick) }
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "add"
                )
            }
        },
        backgroundColor = WhiteSmoke
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                TabRow(
                    selectedTabIndex = tabIndex,
                    backgroundColor = WhiteSmoke,
                    contentColor = Color.Black
                ) {
                    tabData.forEachIndexed { index, text ->
                        Tab(selected = tabIndex == index, onClick = {
                            tabIndex = index
                            if (index == 0) {
                                list.value = tasks
                            } else {
                                dailyTasks.value.forEach { task ->
                                    viewModel.onEvent(TaskListEvent.OnDailyTaskUpdate(task))
                                }
                                list.value = dailyTasks
                            }

                        }, text = {
                            Text(
                                text = text,
                                fontWeight = FontWeight.Bold
                            )
                        })
                    }
                }
            }
            items(list.value.value.sortedBy { it.isDone }) { Task ->
                TaskItem(
                    task = Task,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(TaskListEvent.OnTaskClick(Task))
                        }
                        .padding(
                            start = dimensionResource(id = R.dimen.app_padding),
                            end = dimensionResource(id = R.dimen.app_padding),
                            top = dimensionResource(id = R.dimen.app_little_padding),
                            bottom = dimensionResource(id = R.dimen.app_little_padding)
                        )
                )
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.app_little_spacer_height))
                        .fillMaxWidth()
                )
            }
            item {
                if (list.value.value.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillParentMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_tasks),
                            color = Color.LightGray
                        )
                    }
                }
            }

        }
    }
}

