package com.jooheon.dday.android.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jooheon.dday.android.ext.observeWithLifecycle
import com.jooheon.dday.android.home.component.DdayItem
import com.jooheon.dday.domain.model.HomeUiEvent
import com.jooheon.dday.domain.model.HomeUiState
import com.jooheon.dday.android.home.component.DeleteDialog
import com.jooheon.dday.android.home.component.EmptySection
import com.jooheon.dday.android.home.component.TopSection
import com.jooheon.dday.android.widget.WidgetUtil
import com.jooheon.dday.domain.model.DdayConstant

@Composable
internal fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    LaunchedEffect(uiState) {
        WidgetUtil.update(context)
    }

    viewModel.navigateChannel.observeWithLifecycle {
        navController.navigate(it)
    }

    HomeScreen(
        homeUiState = uiState,
        onEvent = {
            viewModel.dispatch(
                context = context,
                event = it
            )
        },
    )
}

@Composable
private fun HomeScreen(
    homeUiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    var deleteDialogState by remember { mutableStateOf(false) }
    var deleteDialogIdState by remember { mutableLongStateOf(-1L) }

    LaunchedEffect(deleteDialogState) {
        if(!deleteDialogState) {
            deleteDialogIdState = -1L
        }
    }

    Scaffold(
        topBar = {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                TopSection(
                    profileImageUrl = homeUiState.profileImageUrl,
                    onProfileImageSelected = { onEvent.invoke(HomeUiEvent.OnProfileImageSelected(it)) },
                    modifier = Modifier.padding(20.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent.invoke(HomeUiEvent.OnAddButtonClick) },
                containerColor = MaterialTheme.colorScheme.secondary,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add D-day"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                if(homeUiState.items.isEmpty()) {
                    EmptySection()
                    return@Box
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding,
                ) {
                    val ddayConstant = DdayConstant()
                    itemsIndexed(homeUiState.items) { index, item ->
                        DdayItem(
                            item = item,
                            colorRaw = ddayConstant.getColor(index),
                            onCheckedChange = {
                                val event = HomeUiEvent.OnToggleSelected(item)
                                onEvent.invoke(event)
                            },
                            onItemClick = {
                                val event = HomeUiEvent.OnDetailClicked(item)
                                onEvent.invoke(event)
                            },
                            onLongClick = {
                                deleteDialogIdState = item.id
                                deleteDialogState = true
                            }
                        )
                    }
                }

                if(deleteDialogState) {
                    DeleteDialog(
                        id = deleteDialogIdState,
                        onDeleted = {
                            onEvent.invoke(HomeUiEvent.OnDelete(it))
                            deleteDialogState = false
                        },
                        onDismissRequest = {
                            deleteDialogState = false
                        }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen(
            homeUiState = HomeUiState.default,
            onEvent = {}
        )
    }
}