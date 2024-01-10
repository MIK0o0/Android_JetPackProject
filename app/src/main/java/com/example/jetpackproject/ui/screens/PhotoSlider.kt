package com.example.jetpackproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jetpackproject.Data.getBooleanValue
import com.example.jetpackproject.Data.saveStringValue
import com.example.jetpackproject.Photo.PhotoRepo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PhotoSlider(clickedIndex: Int) {
    val context = LocalContext.current
    val photoRepo = PhotoRepo.getInstance(context)
    val photos = photoRepo.getSharedList(getBooleanValue(context, "external"))!!
    val pagerState = rememberPagerState(initialPage = clickedIndex)

    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize(), count = photos.size) { page ->
        val photo = photos[page]
        val bitmap = getBitmapFromUri(context, photo.curi, 2)
        if (bitmap != null) {
            Column(
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = photo.name, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                Button(onClick = { saveStringValue(context, "main_photo", photo.curi.toString()) }) {
                    Text(text = "Set as main photo")
                }
            }
        }
    }
}