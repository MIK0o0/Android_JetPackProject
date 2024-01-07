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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.example.jetpackproject.BuildConfig
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var lastFileUri: Uri
var lastFile: File = File("")

@Composable
fun TakePhoto() {
    val context = LocalContext.current
    val photoLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { result ->
        if (result) {
            // consume result - see later remarks
            Toast.makeText(context, "Photo TAKEN", Toast.LENGTH_LONG).show()
//            saveImageToExternalStorage(context)
        } else {
            // make some action – warning
            Toast.makeText(context, "Photo NOT taken!", Toast.LENGTH_LONG).show()
//            lastFile.delete()
        }
    }
    Button(onClick = {
        lastFileUri = getNewFileUri(context)
        try {
            photoLauncher.launch(lastFileUri)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context,"CAMERA DOESN'T WORK!", Toast.LENGTH_LONG).show()
        }
    }) {
        Text("Go to tak")
    }
}

@Composable
fun SavePhoto() {
    val context = LocalContext.current
    Button(onClick = {
        saveImageToExternalStorage(context)
    }) {
        Text("Save photo")
    }
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
    lastFile = imageFile //save File for future use
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        imageFile
    )
}


fun saveImageToExternalStorage(context: Context) {
    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val externalStorageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val newFile = File(externalStorageDir, lastFile.name)


        var fis: FileInputStream? = null
        var fos: FileOutputStream? = null

        try {
            fis = FileInputStream(lastFile)
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

        lastFile.delete()
    } else{
        Toast.makeText(context, "External storage not available!", Toast.LENGTH_LONG)
            .show()
    }
}
