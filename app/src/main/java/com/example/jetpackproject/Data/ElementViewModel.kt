package com.example.jetpackproject.Data

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.File

class ElementViewModel : ViewModel() {
    var name: String by mutableStateOf("Default Name")
    var spec: String by mutableStateOf("Default Description")
    var strength: Float by mutableStateOf(0f)
    var type: String by mutableStateOf("Bird")
    var isDangerous: Boolean by mutableStateOf(false)
    var photoUri: Uri? by mutableStateOf(null)
    var photoFile: File? by mutableStateOf(null)
}