package com.jooheon.dday.android.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.jooheon.dday.domain.model.Dday
import com.jooheon.dday.domain.model.DdayDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class DdayWidgetReceiver: GlanceAppWidgetReceiver() {
    private val TAG = DdayWidgetReceiver::class.java.simpleName
    override val glanceAppWidget = DdayWidget()

    private val scope = MainScope()

    @Inject
    lateinit var dataSource: DdayDataSource

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ${intent.action}")

        when(intent.action) {
            AppWidgetManager.ACTION_APPWIDGET_UPDATE -> {
                scope.launch {
                    val items = withContext(Dispatchers.IO) {
                        dataSource.getAllDdays()
                    }
                    update(
                        context = context,
                        widget = glanceAppWidget,
                        items = items.filter { it.selected }
                    )
                }
            }
            else -> { /** nothing **/ }
        }

        super.onReceive(context, intent)
    }

    suspend fun update(
        context: Context,
        widget: GlanceAppWidget,
        items: List<Dday>,
    ) {
        val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(DdayWidget::class.java)
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = context,
                definition = PreferencesGlanceStateDefinition,
                glanceId = glanceId,
                updateState = {
                    it.toMutablePreferences().apply {
                        val set = items.map { it.toJsonString() }.toSet()
                        this[stringSetPreferencesKey(KEY_WIDGET_DATA)] = set
                    }
                }
            )
        }

        widget.updateAll(context)
    }

    companion object {
        internal const val KEY_WIDGET_DATA = "widget_data"
    }
}