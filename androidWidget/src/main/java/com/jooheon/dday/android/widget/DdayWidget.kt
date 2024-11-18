package com.jooheon.dday.android.widget

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import com.jooheon.dday.android.widget.DdayWidgetReceiver.Companion.KEY_WIDGET_DATA
import com.jooheon.dday.android.widget.component.DdayItem
import com.jooheon.dday.domain.model.Dday.Companion.fromString
import com.jooheon.dday.domain.model.DdayConstant

class DdayWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                val contents = currentState(stringSetPreferencesKey(KEY_WIDGET_DATA))
                val ddays = contents?.mapNotNull { it.fromString() } ?: emptyList()
                Box(
                    modifier = GlanceModifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    LazyColumn {
                        item {
                            Spacer(modifier = GlanceModifier.height(8.dp))
                        }
                        val ddayConstant = DdayConstant()
                        itemsIndexed(ddays) { index, item ->
                            Column {
                                DdayItem(
                                    item = item,
                                    colorRaw = ddayConstant.getColor(index),
                                )
                                Spacer(modifier = GlanceModifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}