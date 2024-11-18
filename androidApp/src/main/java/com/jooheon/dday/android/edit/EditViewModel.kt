package com.jooheon.dday.android.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jooheon.dday.domain.model.Dday
import com.jooheon.dday.domain.model.DdayDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val dataSource: DdayDataSource
): ViewModel() {
    private val _finishChannel = Channel<Unit>()
    internal val finishChannel = _finishChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(Dday.default)
    internal val uiState = _uiState.asStateFlow()

    private var ddayId = 0L

    internal fun loadData(id: Long) = viewModelScope.launch {
        val items = withContext(Dispatchers.IO) {
            dataSource.getAllDdays()
        }

        val dday = items.firstOrNull { it.id == id } ?: Dday.default

        ddayId = if(dday == Dday.default) {
            (items.maxOfOrNull { it.id } ?: 0) + 1
        } else {
            id
        }

        _uiState.emit(dday)
    }

    internal fun insert(dday: Dday) = viewModelScope.launch {
        dataSource.insertDday(dday.copy(id = ddayId))
        _finishChannel.send(Unit)
    }
}