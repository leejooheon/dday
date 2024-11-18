package com.jooheon.dday.android.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jooheon.dday.domain.model.Dday
import com.jooheon.dday.domain.model.DdayConstant

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DdayItem(
    item: Dday,
    colorRaw: Long,
    onCheckedChange: (Boolean) -> Unit,
    onItemClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .combinedClickable(
                onClick = onItemClick,
                onLongClick = onLongClick,
            ),
        headlineContent = {
            Column {
                Text(
                    text = listOf(item.annualUntilDateFormat(), item.annualDdayFormat())
                        .filter { it.isNotEmpty() }
                        .joinToString(" | "),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = 0.85f,
                        ),
                    )
                )

                if(!item.annualEvent && item.isPastYear()) {
                    Text(
                        text = "${item.fullUntilDateFormat()} | ${item.fullDdayFormat()}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.85f
                            ),
                        )
                    )
                }
            }
        },
        supportingContent = {
            Text(
                text = item.dateFormat(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = 0.7f
                ),
            )
        },
        overlineContent = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color(colorRaw)
                )
            )
        },
        leadingContent = {
            Text(
                text = item.emoji,
                style = MaterialTheme.typography.headlineLarge
            )
        },
        trailingContent = {

            Switch(
                checked = item.selected,
                onCheckedChange = onCheckedChange,
            )
        },
    )
}

@Preview
@Composable
private fun PreviewDdayItem() {
    MaterialTheme {
        DdayItem(
            item = Dday.default,
            colorRaw = DdayConstant().getColor(2),
            onCheckedChange = {},
            onItemClick = {},
            onLongClick = {},
        )
    }
}