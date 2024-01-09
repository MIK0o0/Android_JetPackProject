package com.example.jetpackproject.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.android_project.Data.DataItem
import com.example.android_project.Data.DataRepo
import com.example.jetpackproject.Data.ListViewModel
import com.example.jetpackproject.R



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    val listViewModel = remember { ListViewModel() }
    val items by listViewModel.items.collectAsState()

    @SuppressLint("RememberReturnType")
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ListItem(item: DataItem, onClick: () -> Unit) {
        val dataRepo = DataRepo.getInstance()
        val color = if (item.dangerous) Color.Red else Color.Green
        val showDialog = remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .combinedClickable(onClick = onClick, onLongClick = { showDialog.value = true })
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

        if (showDialog.value) {
            ConfirmDeleteDialog(
                showDialog = true,
                onConfirm = {

                    listViewModel.deleteItem(item)
                    showDialog.value = false
                },
                onDismiss = { showDialog.value = false }
            )
        }
    }

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
                ListItem(item,
                    onClick = { navController.navigate("details/${item.id}") })
            }
        }
    }
}

@Composable
fun ConfirmDeleteDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Confirm Delete") },
            text = { Text("Are you sure you want to delete this item?") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("No")
                }
            }
        )
    }
}



@Composable
fun Thumbnail(uri: Uri) {
    val bitmap = getBitmapFromUri(LocalContext.current, uri, 4)
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

