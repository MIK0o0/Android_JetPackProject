package com.example.jetpackproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.example.jetpackproject.Photo.Photo
import com.example.jetpackproject.Photo.PhotoRepo
import com.example.jetpackproject.ui.screens.getBitmapFromUri
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PhotoSlider(clickedIndex: Int) {
    val context = LocalContext.current
    val photoRepo = PhotoRepo.getInstance(context)
    val photos = photoRepo.getSharedList()!!
    val pagerState = rememberPagerState(initialPage = clickedIndex)

    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize(), count = photos.size) { page ->
        val photo = photos[page]
        val bitmap = getBitmapFromUri(context, photo.curi)
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}