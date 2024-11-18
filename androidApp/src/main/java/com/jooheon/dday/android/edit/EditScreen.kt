package com.jooheon.dday.android.edit

import android.widget.ToggleButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jooheon.dday.android.edit.component.EmojiDialog
import com.jooheon.dday.android.edit.component.TextFieldWithLabel
import com.jooheon.dday.android.ext.observeWithLifecycle
import com.jooheon.dday.domain.model.DateTimeUtil
import com.jooheon.dday.domain.model.Dday

@Composable
internal fun EditScreen(
    navController: NavController,
    id: Long,
    viewModel: EditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData(id)
    }

    viewModel.finishChannel.observeWithLifecycle {
        navController.navigateUp()
    }

    EditScreen(
        dday = uiState,
        onSave = { viewModel.insert(it) },
        onBackPressed = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditScreen(
    dday: Dday,
    onSave: (Dday) -> Unit,
    onBackPressed: () -> Unit,
) {
    val scrollState = rememberScrollState()

    var dateText by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf("") }
    var annualEvent by remember { mutableStateOf(false) }

    var titleError by remember { mutableStateOf(title.isBlank()) }
    var showDialog by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    LaunchedEffect(dday) {
        if(dday != Dday.default) {
            title = dday.title
            dateText = dday.ddayDateFormat()
        }

        annualEvent = dday.annualEvent
        emoji = dday.emoji
        titleError = title.isBlank()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Dday Editor") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { paddingValues -> // paddingValues 파라미터 추가
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // paddingValues 사용
                    .verticalScroll(scrollState)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            showDialog = true
                        },
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = emoji,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 128.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextFieldWithLabel(
                    label = "Title",
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = it.isBlank()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextFieldWithLabel(
                    isError = dateError,
                    value = dateText,
                    errorLabel = "yyyymmdd 형식으로 입력해주세요.",
                    label = "Date",
                    onValueChange = {
                        dateText = it
                        dateError = isError(it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "매년 돌아오는 이벤트 인가요?",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    IconToggleButton(
                        checked = annualEvent,
                        onCheckedChange = { annualEvent = !annualEvent },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = if (annualEvent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.error
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (annualEvent) "Yes" else "No")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onSave.invoke(
                            Dday(
                                id = 0,
                                title = title,
                                emoji = emoji,
                                annualEvent = annualEvent,
                                selected = true,
                                date = DateTimeUtil.yyyymmddToTimeMillis(dateText)!!,
                            )
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    shape = RoundedCornerShape(25.dp),
                    enabled = !(dateError || titleError)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Save")
                }

                Spacer(modifier = Modifier.height(52.dp)) // Space 요소 대체
            }
        }
    )

    if(showDialog) {
        EmojiDialog(
            emoji = emoji,
            onDismissRequest = {
                emoji = it
                showDialog = false
            }
        )
    }
}

private fun isError(dateString: String): Boolean {
    if(dateString.length != 8) return true
    return DateTimeUtil.yyyymmddToTimeMillis(dateString) == null
}

@Preview
@Composable
private fun PreviewEditScreen() {
    MaterialTheme {
        EditScreen(
            dday = Dday.default,
            onSave = {},
            onBackPressed = {}
        )
    }
}