package com.example.jetpackproject.ui.screens

import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.android_project.Data.DataItem
import com.example.android_project.Data.DataRepo
import com.example.jetpackproject.Data.ElementViewModel
import com.example.jetpackproject.Photo.TakePhoto
import com.example.jetpackproject.Photo.savePhoto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController, viewModel: ElementViewModel = viewModel()) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = viewModel.name, onValueChange = { viewModel.name = it }, label = { Text("Name") }, modifier = Modifier.padding(top = 16.dp))
        TextField(value = viewModel.spec, onValueChange = { viewModel.spec = it }, label = { Text("Description") }, modifier = Modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Strength: ${viewModel.strength.toInt()}", style = MaterialTheme.typography.titleMedium)
        Slider(value = viewModel.strength, onValueChange = { viewModel.strength = it }, valueRange = 0f..100f, modifier = Modifier.width(250.dp))
        Text(text = "Type", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
        RadioGroup(options = listOf( "Mammal", "Bird", "Fish", "Reptile", "Amphibian", "Bug"), selectedOption = viewModel.type, onOptionSelected = { viewModel.type = it })
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Dangerous")
            Checkbox(checked = viewModel.isDangerous, onCheckedChange = { viewModel.isDangerous = it }, enabled = true)
        }
        Spacer(modifier = Modifier.size(16.dp))
        TakePhoto(viewModel)
        Spacer(modifier = Modifier.size(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                savePhoto(context, viewModel)
                val item = DataItem(viewModel.name, viewModel.spec, viewModel.strength.toInt(), viewModel.type, viewModel.isDangerous, viewModel.photoUri.toString())
                DataRepo.getInstance().addItem(item)
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
//        SavePhoto()
    }
}



@Composable
fun RadioGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column{
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) }
                    )
                    .background(Color.Transparent)
                    .padding(16.dp)
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) },
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}