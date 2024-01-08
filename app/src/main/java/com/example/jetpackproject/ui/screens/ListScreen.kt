package com.example.jetpackproject.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.android_project.Data.DataItem
import com.example.jetpackproject.Data.ListViewModel
import com.example.jetpackproject.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    val listViewModel = remember { ListViewModel() }
    val items by listViewModel.items.collectAsState()
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                text = { Text("Add") },
                onClick = { navController.navigate("add") },
                modifier = androidx.compose.ui.Modifier
                    .padding(16.dp)
            )
        },
    ) {
        LazyColumn {
            items(items) { item ->
                ListItem(item, onClick = {  })
            }
        }
    }
}

@Composable
fun ListItem(item: DataItem, onClick: () -> Unit) {
    val color = if (item.dangerous) Color.Red else Color.Green

    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .background(color)
            .border(1.dp, Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Thumbnail(uri = item.photo_uri.toUri())
        Text(
            text = item.text_name,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = item.item_type.uppercase(),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
fun Thumbnail(uri: Uri) {
    val bitmap = getBitmapFromUri(LocalContext.current, uri)
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )
    }else{
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )
    }
}

