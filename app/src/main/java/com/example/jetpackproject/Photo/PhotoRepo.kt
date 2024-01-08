package com.example.jetpackproject.Photo

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class PhotoRepo {
    lateinit var uri: Uri
    companion object{
        private var INSTANCE: PhotoRepo? = null
        private lateinit var ctx: Context
        var sharedStoreList: MutableList<Photo>? = null
        fun getInstance(ctx: Context): PhotoRepo {
            if (INSTANCE == null){
                INSTANCE = PhotoRepo()
                sharedStoreList = mutableListOf<Photo>()
                this.ctx = ctx
            }
            return INSTANCE as PhotoRepo
        }
    }

    fun getSharedList(external: Boolean): List<Photo>? {
        if (external) uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        else uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
        sharedStoreList?.clear()

        val contentResolver: ContentResolver = ctx.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor == null) {
            throw NullPointerException("Unknown URI: $uri")
        } else if (!cursor.moveToFirst()) {
            println("No photos")
        } else {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            do {
                val thisId = cursor.getLong(idColumn)
                val thisName = cursor.getString(nameColumn)
                val thisContentUri = ContentUris.withAppendedId(uri, thisId)
                val thisUriPath = thisContentUri.toString()

                sharedStoreList?.add(Photo(thisName, thisUriPath, "No path yet", thisContentUri))
            } while (cursor.moveToNext())
        }
        return sharedStoreList
    }
}