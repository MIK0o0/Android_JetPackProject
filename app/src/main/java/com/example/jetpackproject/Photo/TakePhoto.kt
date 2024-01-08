package com.example.jetpackproject.Photo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.jetpackproject.BuildConfig
import com.example.jetpackproject.Data.ElementViewModel
import com.example.jetpackproject.ui.screens.RadioGroup
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var temporaryFileUri: Uri
var temporaryFile: File = File("")
var directory by mutableStateOf("External")
var isPhotoTaken by mutableStateOf(false)

@Composable
fun TakePhoto(viewModel: ElementViewModel) {
    val context = LocalContext.current
    val photoLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { result ->
        if (result) {
            Toast.makeText(context, "Photo TAKEN", Toast.LENGTH_LONG).show()
            isPhotoTaken = true
            viewModel.photoUri = temporaryFileUri
//            saveImageToExternalStorage(context)
        } else {
            Toast.makeText(context, "Photo NOT taken!", Toast.LENGTH_LONG).show()
            isPhotoTaken = false
//            lastFile.delete()
        }
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        PhotoTakenInfo()
        Button(onClick = {
            temporaryFileUri = getNewFileUri(context)
            try {
                photoLauncher.launch(temporaryFileUri)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context,"CAMERA DOESN'T WORK!", Toast.LENGTH_LONG).show()
            }
        }) {
            Text("Take photo")
        }
    }

    DestinationChooser()
}

@Composable
fun PhotoTakenInfo(){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Is Photo Taken?", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(8.dp))
        if (isPhotoTaken) Checkbox(
            checked = true,
            enabled = false,
            modifier = Modifier.padding(8.dp),
            onCheckedChange = {})
        else Checkbox(
            checked = false,
            enabled = false,
            modifier = Modifier.padding(8.dp),
            onCheckedChange = {})
    }
}

@Composable
fun DestinationChooser(){
    Text(text = "Choose directory to save photo:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp))
    RadioGroup(options = listOf("External", "Internal"), selectedOption = directory, onOptionSelected = { directory = it })
}

fun savePhoto(context: Context, viewModel: ElementViewModel) {
    if (!isPhotoTaken) return
    if (directory == "External")
        saveImageToExternalStorage(context, viewModel)
    else
        saveImageToInternalStorage(context, viewModel)
}


fun getNewFileUri(context: Context): Uri {
    val activity = context as Activity
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_DCIM)
    val innerStorageDir: File? = activity.filesDir
//        val storageDir: File? = requireActivity().filesDir
    val imageFile = File.createTempFile(
        "IMG_${timeStamp}_",
        ".jpg",
        storageDir
    )
    temporaryFile = imageFile //save File for future use
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        imageFile
    )
}

//fun getNewFileUri(context: Context): Uri {
//    val activity = context as Activity
//    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
//    val storageDir: File? = activity.filesDir
//    val imageFile = File.createTempFile(
//        "IMG_${timeStamp}_",
//        ".jpg",
//        storageDir
//    )
//    temporaryFile = imageFile //save File for future use
//    return FileProvider.getUriForFile(
//        context,
//        "${BuildConfig.APPLICATION_ID}.provider",
//        imageFile
//    )
//}


fun saveImageToExternalStorage(context: Context, viewModel: ElementViewModel) {
    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val externalStorageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val newFile = File(externalStorageDir, temporaryFile.name)
        viewModel.photoUri = Uri.fromFile(newFile)

        var fis: FileInputStream? = null
        var fos: FileOutputStream? = null

        try {
            fis = FileInputStream(temporaryFile)
            fos = FileOutputStream(newFile)

            val buffer = ByteArray(1024)
            var length: Int
            while (fis.read(buffer).also { length = it } > 0) {
                fos.write(buffer, 0, length)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fis?.close()
            fos?.close()
        }
        val activity = context as Activity
        MediaStore.Images.Media.insertImage(activity.contentResolver, newFile.path, newFile.name, newFile.name)

        temporaryFile.delete()
    } else{
        Toast.makeText(context, "External storage not available!", Toast.LENGTH_LONG)
            .show()
    }
}

fun saveImageToInternalStorage(context: Context, viewModel: ElementViewModel) {
    val internalStorageDir = context.filesDir
    val newFile = File(internalStorageDir, temporaryFile.name)
    viewModel.photoUri = Uri.fromFile(newFile)

    var fis: FileInputStream? = null
    var fos: FileOutputStream? = null

    try {
        fis = FileInputStream(temporaryFile)
        fos = FileOutputStream(newFile)

        val buffer = ByteArray(1024)
        var length: Int
        while (fis.read(buffer).also { length = it } > 0) {
            fos.write(buffer, 0, length)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        fis?.close()
        fos?.close()
    }
    val activity = context as Activity
    println(viewModel.photoUri)
    // No need to insert the image into the MediaStore when saving to internal storage
    temporaryFile.delete()
}
