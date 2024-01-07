package com.example.android_project.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyDao {
    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun getAllData(): MutableList<DataItem>?

    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: DataItem) : Long

    @Delete
    fun delete(item: DataItem): Int

    @Query("SELECT * FROM item_table WHERE id = :itemId")
    fun getItemById(itemId: Int): DataItem?

    @Query("SELECT * FROM item_table WHERE id = :itemId")
    fun getItemById(itemId: Long): DataItem?

    @Update
    fun update(item: DataItem)
}