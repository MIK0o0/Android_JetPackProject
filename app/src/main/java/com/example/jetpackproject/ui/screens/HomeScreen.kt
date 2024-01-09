package com.example.jetpackproject.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
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
    var setting by remember { mutableStateOf("Directory Info") }
    var mainPhotoUri: String? = getStringValue(context, "main_photo")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "My JetPack Compose Project", style = MaterialTheme.typography.titleMedium)
        Text(text = invitation, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$authorName $authorSurname", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        MainImage(imageUri = mainPhotoUri?.toUri() ?: Uri.EMPTY)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = setting, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun MainImage(imageResourceId: Int){
    Image(
        painter = painterResource(id = imageResourceId),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background)

    )
}

@Composable
fun MainImage(imageUri: Uri){
    val context = LocalContext.current
    val bitmap = getBitmapFromUri(context, imageUri, 2)
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
    }else MainImage(imageResourceId = R.drawable.ic_launcher_foreground)
}



