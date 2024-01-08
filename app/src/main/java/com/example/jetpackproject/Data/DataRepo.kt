package com.example.android_project.Data

import android.content.Context

class DataRepo() {
    private var dataList: MutableList<DataItem>? = null
    private lateinit var myDao: MyDao
    private lateinit var db: MyDB

    companion object {
        private var INSTANCE: DataRepo? = null

         fun getInstance(): DataRepo {
            return INSTANCE as DataRepo
        }
        private fun init(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DataRepo(context, true)
            }
        }
    }

    //create private constructor
    private constructor(context: Context, isPrivate: Boolean) : this() {
        db = MyDB.getDatabase(context)!!
        myDao = db.myDao()!!
    }

    constructor(context: Context) : this() {
        init(context)
    }

    fun getData() : MutableList<DataItem>? {
        dataList = myDao.getAllData()
        return dataList
    }

    fun addItem(item: DataItem): Boolean {
        return myDao.insert(item) >= 0
    }
    fun deleteItem(item: DataItem): Boolean {
        return myDao.delete(item) > 0
    }

    fun isItemExists(dataItem: DataItem): Boolean {
        val itemId = dataItem?.id ?: return false
        val existingItem = myDao.getItemById(itemId)
        return existingItem != null
    }

    fun getItemById(itemId: Int): DataItem? {
        return try {
            myDao.getItemById(itemId)
        }catch (e: Exception) {
            null
        }

    }

    fun updateItem(item: DataItem) {
        myDao.update(item)
    }


}