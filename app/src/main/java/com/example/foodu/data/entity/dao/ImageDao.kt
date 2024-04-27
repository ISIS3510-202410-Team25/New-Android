package com.example.foodu.data.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodu.data.entity.ImageEntity

@Dao
interface ImageDao {
    @Query("SELECT * FROM image_table")
    suspend fun getAllQuotes():List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images:List<ImageEntity>)

    @Query("DELETE FROM image_table")
    suspend fun deleteAllQuotes()
}