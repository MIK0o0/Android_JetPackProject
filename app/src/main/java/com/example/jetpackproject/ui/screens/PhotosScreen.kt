package com.example.jetpackproject.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackproject.Photo.Photo
import com.example.jetpackproject.Photo.PhotoRepo
import java.io.FileNotFoundException
import java.io.InputStream

@Composable
fun PhotoScreen(navController: NavController) {
    val context = LocalContext.current
    val photoRepo = PhotoRepo.getInstance(context)
    val photos = photoRepo.getSharedList()!!
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

@Composable
fun PhotoItem(photo: Photo, index: Int, onClick: (Int) -> Unit = {}) {
    val context = LocalContext.current
    val bitmap = getBitmapFromUri(context, photo.curi)
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(4.dp)
                .clickable(onClick = { onClick(index) }),
            contentScale = ContentScale.Crop,
        )
    }
}

fun getBitmapFromUri(context: Context, uri: Uri?): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = false
            inSampleSize = 4 // Downsample by a factor of 4
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
