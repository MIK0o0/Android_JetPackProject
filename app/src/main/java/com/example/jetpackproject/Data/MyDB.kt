package com.example.android_project.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataItem::class], version = 3)
abstract class MyDB : RoomDatabase() {
    abstract fun myDao(): MyDao
    companion object {
        private var DB_INSTANCE: MyDB? = null
        @Synchronized
        open fun getDatabase(context: Context): MyDB? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MyDB::class.java,
                    "item_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB_INSTANCE
        }
    }
}