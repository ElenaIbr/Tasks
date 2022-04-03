package com.example.tasksapp.ui.task_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.tasksapp.data.Task
import com.example.tasksapp.ui.theme.DoneTaskBackground
import com.example.tasksapp.ui.theme.DoneTaskTextColor
import com.example.tasksapp.R

@Composable
fun TaskItem(
    task: Task,
    onEvent: (TaskListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.app_horizontal_card_padding),
                end = dimensionResource(id = R.dimen.app_horizontal_card_padding),
                top = dimensionResource(id = R.dimen.app_vertical_card_padding)
            ),
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.app_corner_radius)
        ),
        elevation = dimensionResource(id = R.dimen.app_card_elevation),
        backgroundColor = if (task.isDone) DoneTaskBackground else Color(task.color)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (task.isDone) DoneTaskTextColor else Color.Black,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None

                )
            }
            Spacer(
                modifier = Modifier.width(
                    dimensionResource(id = R.dimen.app_big_spacer_height)
                )
            )
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { isDone ->
                    onEvent(TaskListEvent.OnCheckedChange(task, isDone))
                },
                colors = CheckboxDefaults.colors(DoneTaskTextColor)
            )
            Spacer(
                modifier = Modifier.width(
                    dimensionResource(id = R.dimen.app_spacer_height)
                )
            )
            IconButton(onClick = {
                onEvent(TaskListEvent.OnDeleteTaskClick(task))
            }) {
                Image(
                    modifier = Modifier
                        .height(
                            dimensionResource(id = R.dimen.app_icon_height)
                        )
                        .width(
                            dimensionResource(id = R.dimen.app_icon_height)
                        ),
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = stringResource(id = R.string.delete)
                )
            }
        }
    }
}

