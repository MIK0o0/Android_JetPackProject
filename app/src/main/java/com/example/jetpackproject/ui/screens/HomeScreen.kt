package com.example.jetpackproject.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.net.toUri
import com.example.jetpackproject.Data.getStringValue
import com.example.jetpackproject.R

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var invitation by remember { mutableStateOf("Fragment to start on") }
    var authorName by remember { mutableStateOf("Mikołaj") }
    var authorSurname by remember { mutableStateOf("Czyżyk") }
    var imageResourceId by remember { mutableStateOf(R.drawable.ic_launcher_foreground) }
    var setting by remember { mutableStateOf("Directory Info") }
    var mainPhotoUri: String? = getStringValue(context, "main_photo")
    // Your SharedPreferences logic goes here

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header()
        if (mainPhotoUri != null) {
            MainFragmentContent(
                invitation = invitation,
                authorName = authorName,
                authorSurname = authorSurname,
                imageResourceId = mainPhotoUri.toUri(),
                setting = setting
            )
        }else{
            MainFragmentContent(
                invitation = invitation,
                authorName = authorName,
                authorSurname = authorSurname,
                imageResourceId = imageResourceId,
                setting = setting
            )
        }

        Spacer(modifier = Modifier.weight(1f))
//        LazyColumn {
//            items(getSettingsItems()) { settingItem ->
//                SettingItem(settingItem)
//            }
//        }
    }
}

@Composable
fun MainFragmentContent(
    invitation: String,
    authorName: String,
    authorSurname: String,
    imageResourceId: Int,
    setting: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = invitation, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$authorName $authorSurname", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = setting, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun MainFragmentContent(
    invitation: String,
    authorName: String,
    authorSurname: String,
    imageResourceId: Uri,
    setting: String
) {
    val context = LocalContext.current
    val bitmap = getBitmapFromUri(context, imageResourceId, 2)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = invitation, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$authorName $authorSurname", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.background)

            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = setting, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "My JetPack Compose Project", style = MaterialTheme.typography.titleMedium)
    }
}

data class SettingItem(
    val title: String,
    val value: String,
    val icon: ImageVector
)


@Composable
fun SettingItem(settingItem: SettingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = settingItem.icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = settingItem.title, style = MaterialTheme.typography.titleSmall)
            Text(text = settingItem.value, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun getSettingsItems(): List<SettingItem> {
    // Return a list of SettingItem based on your requirements
    return listOf(
        SettingItem("Invitation", "Fragment to start on", Icons.Default.Warning),
        SettingItem("Author Name", "Mikołaj", Icons.Default.Warning),
        SettingItem("Author Surname", "Czyżyk", Icons.Default.Warning),
        SettingItem("Image", "Emoji People", Icons.Default.Home),
        SettingItem("Additional Setting", "Directory Info", Icons.Default.Settings)
    )
}


