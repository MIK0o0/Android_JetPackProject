package com.example.jetpackproject.Data

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.android_project.Data.DataItem
import java.io.File

class ElementViewModel : ViewModel() {
    var name by mutableStateOf("Default Name")
    var spec by mutableStateOf("")
    var strength by mutableStateOf(0f)
    var type by mutableStateOf("Mammal")
    var isDangerous by mutableStateOf(false)
    var photoUri by mutableStateOf<Uri>(Uri.EMPTY)

    fun setValues(dataItem: DataItem) {
        name = dataItem.text_name
        spec = dataItem.text_spec
        strength = dataItem.item_strength.toFloat()
        type = dataItem.item_type
        isDangerous = dataItem.dangerous
        photoUri = dataItem.photo_uri.toUri() // Assuming photoUri is a String
    }
}