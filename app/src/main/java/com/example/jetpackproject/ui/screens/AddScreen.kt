package com.example.jetpackproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android_project.Data.DataItem
import com.example.android_project.Data.DataRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var spec by remember { mutableStateOf("") }
    var strength by remember { mutableStateOf(0f) }
    var type by remember { mutableStateOf("Bird") }
    var isDangerous by remember { mutableStateOf(false) }

    Column {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        TextField(value = spec, onValueChange = { spec = it }, label = { Text("Spec") })
        Slider(value = strength, onValueChange = { strength = it }, valueRange = 0f..100f)
        RadioGroup(options = listOf("Bird", "Fish", "Mammal"), selectedOption = type, onOptionSelected = { type = it })
        Checkbox(checked = isDangerous, onCheckedChange = { isDangerous = it })
        Button(onClick = {
            val item = DataItem(name, spec, strength.toInt(), type, isDangerous)
            // Add item to database
            DataRepo.getInstance().addItem(item)
            navController.popBackStack()
        }) {
            Text("Save")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("Cancel")
        }
    }
}

@Composable
fun RadioGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) }
                    )
                    .background(if (option == selectedOption) Color.Gray else Color.Transparent)
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