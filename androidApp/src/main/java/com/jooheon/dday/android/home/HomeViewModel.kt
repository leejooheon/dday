package com.jooheon.dday.android.home

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jooheon.dday.data.setting.SettingDataStore
import com.jooheon.dday.domain.ScreenNavigation
import com.jooheon.dday.domain.model.DdayDataSource
import com.jooheon.dday.domain.model.HomeUiEvent
import com.jooheon.dday.domain.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataSource: DdayDataSource,
    private val settingDataStore: SettingDataStore
): ViewModel() {
    private val _navigateChannel = Channel<ScreenNavigation>()
    internal val navigateChannel = _navigateChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(HomeUiState.default)
    internal val uiState = _uiState.asStateFlow()

    init {
        collectProfileUrl()
    }

    internal fun loadData() = viewModelScope.launch {
        val items = withContext(Dispatchers.IO) {
            dataSource.getAllDdays()
        }

        _uiState.update {
            it.copy(items = items)
        }
    }

    internal fun dispatch(context: Context, event: HomeUiEvent) = viewModelScope.launch {
        when(event) {
            is HomeUiEvent.OnAddButtonClick -> {
                _navigateChannel.send(ScreenNavigation.Edit(-1))
            }
            is HomeUiEvent.OnDetailClicked -> {
                _navigateChannel.send(ScreenNavigation.Edit(event.model.id))
            }
            is HomeUiEvent.OnDelete -> {
                dataSource.deleteDdayById(event.id)
                loadData()
            }
            is HomeUiEvent.OnToggleSelected -> {
                val selected = !event.model.selected
                dataSource.insertDday(event.model.copy(selected = selected))
                loadData()
            }
            is HomeUiEvent.OnProfileImageSelected -> {
                val path = saveProfileImage(context, event)
                settingDataStore.putProfileUrl(path)
            }
        }
    }

    private fun collectProfileUrl() = viewModelScope.launch {
        settingDataStore.profileUrlFlow().collectLatest { url ->
            _uiState.update {
                it.copy(profileImageUrl = url)
            }
        }
    }

    private fun saveProfileImage(
        context: Context,
        event: HomeUiEvent.OnProfileImageSelected
    ): String {
        initFiles(context)

        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(event.url.toUri())
        val file = File(context.externalCacheDir, "${PROFILE_DIRECTORY}/${generateRandomFileName()}")
        inputStream?.use { input ->
            val outputStream = FileOutputStream(file)
            input.copyTo(outputStream)
            outputStream.close()
        }

        return file.absolutePath
    }

    private fun initFiles(context: Context) {
        File(context.externalCacheDir, PROFILE_DIRECTORY).also { directory ->
            if (!directory.exists()) directory.mkdir()
            directory.listFiles()?.forEach { it.delete() }
        }
    }

    private fun generateRandomFileName(): String {
        return UUID.randomUUID().toString().replace("-", "").take(16) + ".jpg"
    }

    companion object {
        private const val PROFILE_DIRECTORY = "/profile"
    }
}