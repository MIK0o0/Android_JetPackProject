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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.android_project.Data.DataItem
import com.example.android_project.Data.DataRepo
import com.example.jetpackproject.Data.ElementViewModel
import java.io.File

@Composable
fun DetailsScreen(navController: NavController, dataItemId: Int, myViewModel: ElementViewModel = viewModel()) {
    val dataRepo = DataRepo.getInstance()
    val dataItem: DataItem = dataRepo.getItemById(dataItemId)!!
    myViewModel.setValues(dataItem)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Name: ${myViewModel.name}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Description: ${myViewModel.spec}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Strength: ${myViewModel.strength}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Type: ${myViewModel.type}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Dangerous: ${if (myViewModel.isDangerous) "Yes" else "No"}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Photo: ", style = MaterialTheme.typography.titleMedium)
        val context = LocalContext.current
        val bitmap = getBitmapFromUri(context,myViewModel.photoUri , 2)
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
        Button(onClick = { navController.navigate("edit/${dataItem.id}") }) {
            Text(text = "Edit")
        }


    }
}