package com.example.jetpackproject.Data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_project.Data.DataItem
import com.example.android_project.Data.DataRepo
import com.example.android_project.Data.MyDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel() : ViewModel() {
    private val dataRepo = DataRepo.getInstance()
    private val _items = MutableStateFlow<List<DataItem>>(emptyList())
    val items: StateFlow<List<DataItem>> = _items

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _items.value = dataRepo.getData() ?: emptyList()
        }
    }

    fun addItem(item: DataItem) {
        viewModelScope.launch {
            dataRepo.addItem(item)
            fetchItems()
        }
    }

    fun deleteItem(item: DataItem) {
        viewModelScope.launch {
            dataRepo.deleteItem(item)
            fetchItems()
        }
    }
}