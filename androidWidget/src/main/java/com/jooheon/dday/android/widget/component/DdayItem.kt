package com.jooheon.dday.android.widget.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import com.jooheon.dday.android.widget.WidgetUtil
import com.jooheon.dday.domain.model.Dday

@Composable
internal fun DdayItem(
    item: Dday,
    colorRaw: Long,
) {
    Row(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .cornerRadius(8.dp)
            .padding(8.dp)
            .clickable(WidgetUtil.launchAction(LocalContext.current)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = GlanceModifier.width(8.dp))
        DdayText(
            text = item.emoji,
            fontSize = 20.sp,
        )
        Spacer(modifier = GlanceModifier.width(4.dp))

        DdayText(
            text = item.title,
            color = Color(colorRaw),
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = GlanceModifier.width(4.dp))

        DdayText(
            text = if(item.annualEvent) {
                item.annualUntilDateFormat()
            } else {
                item.fullUntilDateFormat()
            },
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.7f
            )
        )
        Spacer(modifier = GlanceModifier.width(4.dp))

        DdayText(
            text = if(item.annualEvent) {
                item.annualDdayFormat()
            } else {
                item.fullDdayFormat()
            },
            fontSize = if(item.annualEvent) 10.sp else 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.85f
            )
        )
    }
}