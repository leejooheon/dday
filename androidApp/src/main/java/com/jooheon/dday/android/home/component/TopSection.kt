package com.jooheon.dday.android.home.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jooheon.dday.android.R

@Composable
fun TopSection(
    profileImageUrl: String,
    onProfileImageSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onProfileImageSelected.invoke(it.toString()) }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .clickable { imagePickerLauncher.launch("image/*") },
            painter = if(profileImageUrl.isEmpty()) {
                painterResource(id = R.drawable.ic_profile)
            } else {
                rememberAsyncImagePainter(profileImageUrl)
            },
            contentScale = ContentScale.Crop,
            contentDescription = "profile image"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row {
            Icon(
                imageVector = Icons.Outlined.DateRange ,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Date Reminder",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }

}

@Preview
@Composable
private fun PreviewTopSection() {
    MaterialTheme {
        TopSection(
            profileImageUrl = "",
            onProfileImageSelected = {}
        )
    }
}
