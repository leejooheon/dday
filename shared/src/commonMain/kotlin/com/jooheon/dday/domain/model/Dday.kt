package com.jooheon.dday.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.math.abs

@Serializable
data class Dday(
    val id: Long,
    val title: String,
    val emoji: String,
    val annualEvent: Boolean,
    val selected: Boolean,
    val date: Long,
) {
    companion object {
        val default = Dday(
            id = 0,
            title = "default",
            emoji = "\uD83D\uDE00",
            annualEvent = true,
            selected = true,
            date = 0,
        )

        val previewList = listOf(
            Dday(
                id = 1,
                title = "preview-1",
                emoji = "\uD83D\uDE00",
                annualEvent = false,
                selected = true,
                date = 1830374750120,
            ),
            Dday(
                id = 2,
                title = "preview-2",
                emoji = "\uD83D\uDE00",
                annualEvent = false,
                selected = false,
                date = 1731374750120,
            ),
            Dday(
                id = 3,
                title = "preview-3",
                emoji = "\uD83D\uDE00",
                annualEvent = false,
                selected = true,
                date = 1631350750120,
            ),
        )
        fun String.fromString(): Dday? {
            return try {
                Json.decodeFromString(serializer(), this)
            } catch (e: SerializationException) {
                null
            }
        }
    }
    fun toJsonString() = Json.encodeToString(this)
    fun fullDdayFormat(): String {
        val dday = DateTimeUtil.calculateFullDday(date)
        val abs = abs(dday)

        return when {
            dday < 0 -> "D+$abs"
            dday > 0 -> "D-$abs"
            else -> "D-day"
        }
    }
    fun annualDdayFormat(): String {
        val dday = DateTimeUtil.calculateDday(date)
        val abs = abs(dday)

        return when {
            dday < 0 -> "D+$abs"
            dday > 0 -> "D-$abs"
            else -> "D-day"
        }
    }
    fun fullUntilDateFormat() = DateTimeUtil.calculateFullDdayDistance(date)
    fun annualUntilDateFormat() = DateTimeUtil.calculateDdayDistance(date)

    fun ddayDateFormat() = DateTimeUtil.ddayDateFormat(date)
    fun dateFormat() = DateTimeUtil.dateFormat(date)

    fun isPastYear() = DateTimeUtil.isPastYear(date)
}