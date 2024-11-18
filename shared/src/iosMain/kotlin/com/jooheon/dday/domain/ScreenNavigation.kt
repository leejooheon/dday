package com.jooheon.dday.domain

actual sealed class ScreenNavigation {
    data class Edit(val id: Long) : ScreenNavigation()
}