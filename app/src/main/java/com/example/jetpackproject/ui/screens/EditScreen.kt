package com.example.jetpackproject.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.android_project.Data.DataItem
import com.example.android_project.Data.DataRepo
import com.example.jetpackproject.Data.ElementViewModel
import com.example.jetpackproject.Photo.TakePhoto
import com.example.jetpackproject.Photo.savePhoto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavController, dataItemId: Int, viewModel: ElementViewModel = viewModel()) {
    val context = LocalContext.current
    val dataRepo = DataRepo.getInstance()
    val dataItem: DataItem? = dataRepo.getItemById(dataItemId)

    if (dataItem == null) {
        Toast.makeText(context, "Item not found", Toast.LENGTH_LONG).show()
        navController.popBackStack()
        return
    }

    var name = remember { mutableStateOf(dataItem.text_name) }
    var spec = remember { mutableStateOf(dataItem.text_spec) }
    var strength = remember { mutableStateOf(dataItem.item_strength.toFloat()) }
    var type = remember { mutableStateOf(dataItem.item_type) }
    var isDangerous = remember { mutableStateOf(dataItem.dangerous) }
//    var photoUri = remember { mutableStateOf(dataItem.photo_uri.toUri()) }


    // Initialize the ViewModel with the dataItem's properties
//    viewModel.name = dataItem.text_name
//    viewModel.spec = dataItem.text_spec
//    viewModel.strength = dataItem.item_strength.toFloat()
//    viewModel.type = dataItem.item_type
//    viewModel.isDangerous = dataItem.dangerous
    viewModel.photoUri = dataItem.photo_uri.toUri()
//    viewModel.name = "Alalalalalalal"

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = name.value, onValueChange = { name.value = it }, label = { Text("Name") }, modifier = Modifier.padding(top = 16.dp))
        TextField(value = spec.value, onValueChange = { spec.value = it }, label = { Text("Description") }, modifier = Modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Strength: ${strength.value.toInt()}", style = MaterialTheme.typography.titleMedium)
        Slider(value = strength.value, onValueChange = { strength.value = it }, valueRange = 0f..100f, modifier = Modifier.width(250.dp))
        Text(text = "Type", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
        RadioGroup(options = listOf( "Mammal", "Bird", "Fish", "Reptile", "Amphibian", "Bug"), selectedOption = type.value, onOptionSelected = { type.value = it })
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Dangerous")
            Checkbox(checked = isDangerous.value, onCheckedChange = { isDangerous.value = it }, enabled = true)
        }
        Spacer(modifier = Modifier.size(16.dp))
        TakePhoto(viewModel)
        Spacer(modifier = Modifier.size(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                savePhoto(context, viewModel)
                val updatedItem = DataItem(name.value, spec.value, strength.value.toInt(), type.value, isDangerous.value, viewModel.photoUri.toString())
                updatedItem.id = dataItemId // Set the id to the original item's id
                dataRepo.updateItem(updatedItem)
                navController.popBackStack()
            },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text("Save")
            }
            Button(onClick = { navController.popBackStack() },
                modifier = Modifier.padding(end = 16.dp)) {
                Text("Cancel")
            }
        }
    }
}