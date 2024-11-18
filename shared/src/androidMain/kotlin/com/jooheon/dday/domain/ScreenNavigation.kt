package com.jooheon.dday.domain

import kotlinx.serialization.Serializable

@Serializable
actual sealed class ScreenNavigation {
    @Serializable
    data object Home: ScreenNavigation()

    @Serializable
    data class Edit(val id: Long): ScreenNavigation()
}