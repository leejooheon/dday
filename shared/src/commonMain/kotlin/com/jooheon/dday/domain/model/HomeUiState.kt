package com.jooheon.dday.domain.model

data class HomeUiState(
    val profileImageUrl: String,
    val items: List<Dday>,
) {
    companion object {
        val default = HomeUiState(
            profileImageUrl = "",
            items = listOf(
                Dday.default, Dday.default, Dday.default,
            ),
        )
    }
}