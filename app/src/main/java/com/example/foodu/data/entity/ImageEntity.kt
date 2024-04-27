package com.example.foodu.data.entity

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodu.model.service.module.Image
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date
@Entity(tableName = "image_table")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")val id:Int = 0,
    @ColumnInfo(name="image")val image:String
)

fun Image.toDatabase() = ImageEntity(image = image)