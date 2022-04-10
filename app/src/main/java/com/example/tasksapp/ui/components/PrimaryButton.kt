package com.example.tasksapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.example.tasksapp.R
import com.example.tasksapp.ui.theme.AccentColor
import com.example.tasksapp.ui.theme.Typography
import com.example.tasksapp.ui.theme.WhiteSmoke

@Composable
fun PrimaryButton(
    title: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = Modifier
            .padding(
                dimensionResource(id = R.dimen.app_padding)
            )
            .fillMaxWidth()
            .height(
                dimensionResource(id = R.dimen.app_element_height)
            )
            .clip(
                RoundedCornerShape(
                    dimensionResource(id = R.dimen.app_padding)
                )
            ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = dimensionResource(id = R.dimen.app_default),
            pressedElevation = dimensionResource(id = R.dimen.app_default),
            disabledElevation = dimensionResource(id = R.dimen.app_default)
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AccentColor,
            disabledBackgroundColor = Color.LightGray,
            contentColor = Color.Black,
            disabledContentColor = Color.Black
        ),
        enabled = enabled,
        onClick = {
            onClick.invoke()
        },
    ) {
        Text(
            text = title,
            color = WhiteSmoke,
            fontSize = Typography.body1.fontSize
        )
    }
}