package com.example.foodu.model.service.module

import com.example.foodu.data.entity.ImageEntity
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Image(val image:String)

//fun ImageModel.toDomain() = Image(image)
fun ImageEntity.toDomain() = Image(image )