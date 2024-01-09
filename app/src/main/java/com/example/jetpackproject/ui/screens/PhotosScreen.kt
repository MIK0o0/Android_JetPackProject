package com.example.jetpackproject.ui.screens

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackproject.Data.getBooleanValue
import com.example.jetpackproject.Data.saveBooleanValue
import com.example.jetpackproject.Photo.Photo
import com.example.jetpackproject.Photo.PhotoRepo
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(navController: NavController) {
    val context = LocalContext.current
    val photoRepo = PhotoRepo.getInstance(context)
    var external = getBooleanValue(context, "external")
    var photos = photoRepo.getSharedList(external)!!

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { expanded = !expanded
                    println(expanded)},
                modifier = Modifier
                    .padding(16.dp),
                content = {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            )
        },
    ){
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset((-10).dp,   0.dp),
            content = {
                Text(text = "Source of images", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp))
                DropdownMenuItem(text = { Text("External") }, onClick = {
                    expanded = false
                    if (!external) {
                        external = true
                        saveBooleanValue(context, "external", true)
                        photos = photoRepo.getSharedList(external)!!
                    }
                })
                DropdownMenuItem(text = { Text("Internal") }, onClick = {
                    expanded = false
                    if (external) {
                        external = false
                        saveBooleanValue(context, "external", false)
                        photos = photoRepo.getSharedList(external)!!
                    }})
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(photos) { index, photo ->
                PhotoItem(photo, index) { clickedIndex ->
                    navController.navigate("slider/$clickedIndex")
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoItem(photo: Photo, index: Int, onClick: (Int) -> Unit = {}) {
    val context = LocalContext.current
    val bitmap = getBitmapFromUri(context, photo.curi, 4)
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(4.dp)
                .combinedClickable(
                    onClick = { onClick(index) }
                ),
            contentScale = ContentScale.Crop,
        )
    }
}

fun getBitmapFromUri(context: Context, uri: Uri?, downSample :  Int): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = false
            inSampleSize = downSample // Downsample by a factor of 4
        }
        val imageStream: InputStream? = uri?.let {
            context.contentResolver.openInputStream(it)
        }
        bitmap = BitmapFactory.decodeStream(imageStream, null, options)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    return bitmap
}


