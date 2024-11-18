package com.jooheon.dday.domain.model

import kotlinx.datetime.*
import kotlin.math.abs

object DateTimeUtil {
    private val now get() = Clock.System.now()
    private val Instant.date get() = this.toLocalDateTime(TimeZone.currentSystemDefault()).date

    fun calculateFullDday(targetMillis: Long): Long {
        val targetDate = Instant.fromEpochMilliseconds(targetMillis).date
        return now.date.daysUntil(targetDate).toLong()
    }

    fun calculateDday(targetMillis: Long): Long {
        val dateTime = Instant.fromEpochMilliseconds(targetMillis).date
        val adjustedTargetDate = LocalDate(
            year = now.date.year,
            month = dateTime.month,
            dayOfMonth = dateTime.dayOfMonth
        )
        return now.date.daysUntil(adjustedTargetDate).toLong()
    }

    fun isPastYear(targetMillis: Long): Boolean {
        val targetDate = Instant.fromEpochMilliseconds(targetMillis).date
        return targetDate.year < now.date.year
    }

    fun calculateFullDdayDistance(targetMillis: Long): String {
        val targetDate = Instant.fromEpochMilliseconds(targetMillis).date
        val period = now.date.periodUntil(targetDate)

        val years = abs(period.years)
        val months = abs(period.months)
        val days = abs(period.days)

        val yearString = if (years > 0) "${years}년" else ""
        val monthString = if (months > 0) "${months}개월" else ""
        val dayString = if (days > 0) "${days}일" else ""

        return listOf(yearString, monthString, dayString)
            .filter { it.isNotEmpty() }
            .joinToString(" ")
    }

    fun calculateDdayDistance(targetMillis: Long): String {
        val targetDate = Instant.fromEpochMilliseconds(targetMillis).date
        val adjustedTargetDate = LocalDate(
            year = now.date.year,
            month = targetDate.month,
            dayOfMonth = targetDate.dayOfMonth
        )
        val period = now.date.periodUntil(adjustedTargetDate)

        val years = abs(period.years)
        val months = abs(period.months)
        val days = abs(period.days)

        val yearString = if (years > 0) "${years}년" else ""
        val monthString = if (months > 0) "${months}개월" else ""
        val dayString = if (days > 0) "${days}일" else ""

        return listOf(yearString, monthString, dayString)
            .filter { it.isNotEmpty() }
            .joinToString(" ")
    }


    fun dateFormat(timeMillis: Long): String {
        val dateTime = Instant.fromEpochMilliseconds(timeMillis).date

        val year = dateTime.year
        val month = if(dateTime.month.number < 10) "0${dateTime.month.number}" else dateTime.month.number
        val day = if(dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
        val dayOfWeek = toKoreanDayOfWeek(dateTime.dayOfWeek)
        return "${year}.${month}.${day}(${dayOfWeek})"
    }

    fun ddayDateFormat(timeMillis: Long): String {
        val dateTime = Instant.fromEpochMilliseconds(timeMillis).date

        val year = dateTime.year
        val month = if(dateTime.month.number < 10) "0${dateTime.month.number}" else dateTime.month.number
        val day = if(dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
        return "${year}${month}${day}"
    }

    fun yyyymmddToTimeMillis(dateString: String): Long? {
        return try {
            val year = dateString.substring(0, 4).toInt()
            val month = dateString.substring(4, 6).toInt()
            val day = dateString.substring(6, 8).toInt()

            // LocalDate에서 Instant로 변환 (UTC 시작 기준)
            val localDate = LocalDate(year, month, day)
            val instant = localDate.atStartOfDayIn(TimeZone.UTC)

            // Instant를 밀리초로 변환
            instant.toEpochMilliseconds()
        } catch (e: Exception) {
            null
        }
    }

    private fun toKoreanDayOfWeek(dayOfWeek: DayOfWeek): String {
        return when(dayOfWeek) {
            DayOfWeek.MONDAY -> "월"
            DayOfWeek.TUESDAY -> "화"
            DayOfWeek.WEDNESDAY -> "수"
            DayOfWeek.THURSDAY -> "목"
            DayOfWeek.FRIDAY -> "금"
            DayOfWeek.SATURDAY -> "토"
            DayOfWeek.SUNDAY -> "일"
            else -> throw IllegalTimeZoneException("Invalid day of week: $dayOfWeek")
        }
    }
}