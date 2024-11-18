package com.jooheon.dday.android.widget.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextDefaults
import androidx.glance.unit.ColorProvider

@Composable
internal fun DdayText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null
) {

    Text(
        text = text,
        style = TextDefaults.defaultTextStyle.copy(
            color = ColorProvider(color),
            fontSize = fontSize,
            fontWeight = fontWeight,
        )
    )
}