package com.example.foodu.repository

import com.example.foodu.data.entity.ImageEntity
import com.example.foodu.data.entity.dao.ImageDao
import com.example.foodu.model.service.module.Image
import com.example.foodu.model.service.module.toDomain
import javax.inject.Inject

class ImageRepository @Inject constructor(
    //private val api: ImageService,
  private val imageDao: ImageDao
)
{
    //uspend fun getAllImagesFromApi(): List<Image>{
    //    val response = api.getImages()
    //    return response.map {it.toDomain()}
    //}
    suspend fun getAllImagesFromDatabase(): List<Image>{
        val response = imageDao.getAllQuotes()
        return response.map { it.toDomain()}
    }

    suspend fun insertImages(images:List<ImageEntity>){
        imageDao.insertAll(images)
    }

    suspend fun clearImages()
    {
        imageDao.deleteAllQuotes()
    }
}