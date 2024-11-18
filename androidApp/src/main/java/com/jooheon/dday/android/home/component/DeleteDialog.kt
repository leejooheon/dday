package com.jooheon.dday.android.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jooheon.dday.android.ext.bounceClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DeleteDialog(
    id: Long,
    onDeleted: (Long) -> Unit,
    onDismissRequest: () -> Unit,
) {
    BasicAlertDialog(
        modifier = Modifier,
        onDismissRequest = onDismissRequest,
    ) {
        LazyColumn {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(1f)
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "Do you want delete?",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button (
                            onClick = { onDeleted.invoke(id) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.bounceClick(),
                        ) {
                            Text(
                                text = "delete",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewDeleteDialog() {
    MaterialTheme {
        DeleteDialog(
            id = 1L,
            onDeleted = {},
            onDismissRequest = {},
        )
    }
}