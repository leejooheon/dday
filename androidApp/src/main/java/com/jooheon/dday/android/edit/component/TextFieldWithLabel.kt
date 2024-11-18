package com.jooheon.dday.android.edit.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
internal fun TextFieldWithLabel(
    label: String,
    value: String,
    errorLabel: String = "",
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        isError = isError,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        label = { Text(text = if(isError) errorLabel else label) },
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = null
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    )
}