package com.example.tasksapp.data

import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tasksapp.ui.theme.*

@Entity
data class Task(
    val title: String,
    val description: String?,
    val isDone: Boolean,
    val taskForToday: Boolean,
    val time: Long,
    val color: Int = PinkBackgroundColor.toArgb(),
    val textColor: Int = PinkTextColor.toArgb(),
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val taskColors = hashMapOf(
            PinkBackgroundColor to PinkTextColor,
            BlueBackgroundColor to BlueTextColor,
            GreenBackgroundColor to GreenTextColor,
            VioletBackgroundColor to VioletTextColor,
            SkyBlueBackgroundColor to SkyBlueTextColor,
        )
        /*val taskColors = listOf(
            PinkBackgroundColor,
            BlueBackgroundColor,
            GreenBackgroundColor,
            VioletBackgroundColor,
            SkyBlueBackgroundColor
        )
        val textTaskColors = listOf(
            PinkTextColor,
            BlueTextColor,
            GreenTextColor,
            VioletTextColor,
            SkyBlueTextColor
        )*/
    }
}
