package com.example.tasksapp.ui.single_task

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tasksapp.R
import com.example.tasksapp.data.Task
import com.example.tasksapp.ui.components.PrimaryButton
import com.example.tasksapp.ui.theme.AccentColor
import com.example.tasksapp.ui.theme.DoneTaskBackground
import com.example.tasksapp.ui.theme.WhiteSmoke
import com.example.tasksapp.utilits.UiEvent
import kotlinx.coroutines.flow.collect

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditTodoScreen(
    OnBackStack: () -> Unit,
    viewModel: SingleTaskViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.OnBackStack -> OnBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> {}
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(
                        dimensionResource(id = R.dimen.app_bar_height)
                    ),
                title = {
                    Text(
                        text = viewModel.title.ifEmpty {
                            stringResource(id = R.string.new_task)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { OnBackStack.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                backgroundColor = WhiteSmoke
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        WhiteSmoke
                    )
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .background(
                                if (viewModel.task?.isDone == true) {
                                    DoneTaskBackground
                                } else {
                                    viewModel.color
                                }
                            )
                            .fillParentMaxHeight(0.13F)
                    ) {
                        TextField(
                            value = viewModel.title,
                            onValueChange = {
                                viewModel.onEvent(SingleTaskEvent.OnTitleChange(it))
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.title),
                                    color = Color.Gray,
                                    fontSize = 20.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    dimensionResource(id = R.dimen.app_card_padding)
                                ),
                            shape = RoundedCornerShape(percent = 10),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.DarkGray,
                                disabledTextColor = Color.Transparent,
                                backgroundColor =
                                if (viewModel.task?.isDone == true) {
                                    DoneTaskBackground
                                } else {
                                    viewModel.color
                                },
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier.height(
                            dimensionResource(id = R.dimen.app_spacer_height)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                if (viewModel.task?.isDone == true) {
                                    DoneTaskBackground
                                } else {
                                    viewModel.color
                                }
                            )
                    ) {
                        TextField(
                            value = viewModel.description,
                            onValueChange = {
                                viewModel.onEvent(SingleTaskEvent.OnDescriptionChange(it))
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.description),
                                    color = Color.Gray,
                                    fontSize = 20.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .fillParentMaxHeight(0.3F)
                                .padding(
                                    dimensionResource(id = R.dimen.app_card_padding)
                                ),
                            singleLine = false,
                            maxLines = 8,
                            shape = RoundedCornerShape(percent = 3),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.DarkGray,
                                disabledTextColor = Color.Transparent,
                                backgroundColor =
                                if (viewModel.task?.isDone == true) {
                                    DoneTaskBackground
                                } else {
                                    viewModel.color
                                },
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
                item {
                    Column {
                        Box(
                            modifier = Modifier
                                .padding(
                                    top = dimensionResource(id = R.dimen.app_padding),
                                    start = dimensionResource(id = R.dimen.app_padding),
                                    bottom = dimensionResource(id = R.dimen.app_padding)
                                )
                        ) {
                            Text(
                                text = stringResource(id = R.string.choose_color),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )
                        }
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = dimensionResource(id = R.dimen.app_padding),
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            contentPadding = PaddingValues(
                                start = dimensionResource(id = R.dimen.app_padding),
                                end = dimensionResource(id = R.dimen.app_padding)
                            )
                        ) {
                            Task.taskColors.forEach { color ->
                                item {
                                    Box(
                                        modifier = Modifier
                                            .size(
                                                dimensionResource(id = R.dimen.app_element_height)
                                            )
                                            .shadow(
                                                dimensionResource(id = R.dimen.app_shadow),
                                                RoundedCornerShape(30)
                                            )
                                            .background(color.key)
                                            .clickable {
                                                viewModel.onEvent(
                                                    SingleTaskEvent.OnColorChange(
                                                        color
                                                    )
                                                )
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.app_padding),
                                bottom = dimensionResource(id = R.dimen.app_padding),
                                top = dimensionResource(id = R.dimen.app_padding)
                            )
                            .fillParentMaxHeight(0.05F)
                    ) {
                        Checkbox(
                            checked = viewModel.taskForToday,
                            onCheckedChange = { isDone ->
                                viewModel.onEvent(SingleTaskEvent.OnDailyStatusChange(isDone))
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = AccentColor,
                                uncheckedColor = Color.LightGray
                            )
                        )
                        Spacer(
                            modifier = Modifier.width(
                                dimensionResource(id = R.dimen.app_spacer_height)
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.daily_task),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }
                }
                item {
                    PrimaryButton(
                        title = stringResource(id = R.string.save_label),
                        onClick = {
                            viewModel.onEvent(SingleTaskEvent.OnSaveTaskClick)
                        },
                        enabled = viewModel.title.isNotEmpty()
                    )
                }
            }
        }
    )
}
