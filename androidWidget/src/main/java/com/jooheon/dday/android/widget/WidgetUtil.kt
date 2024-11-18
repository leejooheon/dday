package com.jooheon.dday.android.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.action.actionStartActivity

object WidgetUtil {
    fun update(context: Context) {
        val command = Intent(context, DdayWidgetReceiver::class.java).apply {
            setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            setPackage(context.packageName)
        }
        context.sendBroadcast(command)
    }

    internal fun launchAction(context: Context) = actionStartActivity(activityIntent(context))
    private fun activityIntent(context: Context) =
        Intent(context.packageManager.getLaunchIntentForPackage(context.packageName))
}