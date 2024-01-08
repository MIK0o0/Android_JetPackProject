package com.example.jetpackproject.ui.screens

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.telecom.Call.Details
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.android_project.Data.DataItem
import com.example.android_project.Data.DataRepo
import java.io.File

@Composable
fun DetailsScreen(navController: NavController, dataItemId: Int) {
    val dataRepo = DataRepo.getInstance()
    val dataItem: DataItem? = dataRepo.getItemById(dataItemId)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (dataItem == null) {
            Toast.makeText(LocalContext.current, "Item not found", Toast.LENGTH_LONG).show()
            navController.popBackStack()
            return@Column
        }else{
            Text(text = "Name: ${dataItem.text_name}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Description: ${dataItem.text_spec}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Strength: ${dataItem.item_strength}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Type: ${dataItem.item_type}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Dangerous: ${if (dataItem.dangerous) "Yes" else "No"}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Photo: ", style = MaterialTheme.typography.titleMedium)
            val context = LocalContext.current
            val bitmap = getBitmapFromUri(context,dataItem.photo_uri.toUri() , 2)
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
            Spacer(modifier = Modifier.size(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    navController.navigateUp()
//                dataItem.photo_uri?.let { it1 -> deletePhoto(context, it1.toUri()) }
//                    if (dataItem != null) {
                        dataRepo.deleteItem(dataItem)
//                    }
                }) {
                    Text(text = "Delete")
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = { navController.navigate("edit/${dataItem.id}") }) {
                    Text(text = "Edit")
                }
            }
        }

    }
}
fun deletePhoto(context: Context, uri: Uri): Boolean {
    val filePath = getFilePathFromUri(context, uri)

    if (filePath != null) {
        val file = File(filePath)
        if (file.exists()) {
            return file.delete()
        }
    }

    return false
}

private fun getFilePathFromUri(context: Context, uri: Uri): String? {
    if (ContentResolver.SCHEME_FILE == uri.scheme) {
        return uri.path
    }

    if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
    }

    return null
}