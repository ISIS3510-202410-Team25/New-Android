package com.example.foodu.data.entity

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import com.example.foodu.data.entity.dao.ImageDao

@Database(entities = [ImageEntity::class], version=1)
abstract class ImageDatabase: RoomDatabase(){
    abstract fun getImageDao():ImageDao
}