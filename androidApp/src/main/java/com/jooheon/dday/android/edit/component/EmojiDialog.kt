package com.jooheon.dday.android.edit.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.composeemojipicker.ComposeEmojiPickerBottomSheetUI
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmojiDialog(
    emoji: String,
    onDismissRequest: (String) -> Unit,
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    var selectedEmoji by remember { mutableStateOf(emoji) }
    var searchText by remember { mutableStateOf("") }

    ModalBottomSheet(
        sheetState = sheetState,
        shape = RectangleShape,
        tonalElevation = 0.dp,
        onDismissRequest = { onDismissRequest.invoke(selectedEmoji) },
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            ComposeEmojiPickerBottomSheetUI(
                onEmojiClick = { emoji ->
                    selectedEmoji = emoji.character
                    onDismissRequest.invoke(selectedEmoji)
                },
                onEmojiLongClick = { emoji ->
                    Toast.makeText(
                        context,
                        emoji.unicodeName.capitalize(Locale.getDefault()),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                searchText = searchText,
                updateSearchText = { updatedSearchText ->
                    searchText = updatedSearchText
                },
            )
        }
    }
}