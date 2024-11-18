package com.jooheon.dday.domain.model

sealed interface HomeUiEvent {
    data object OnAddButtonClick: HomeUiEvent
    data class OnDetailClicked(val model: Dday): HomeUiEvent
    data class OnDelete(val id: Long): HomeUiEvent
    data class OnToggleSelected(val model: Dday): HomeUiEvent
    data class OnProfileImageSelected(val url: String): HomeUiEvent
}