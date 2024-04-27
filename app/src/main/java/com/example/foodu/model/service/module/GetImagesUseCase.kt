package com.example.foodu.model.service.module

import com.example.foodu.data.entity.toDatabase
import com.example.foodu.model.service.AccountService
import com.example.foodu.model.service.impl.AccountServiceImpl
import com.example.foodu.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repository : ImageRepository){
   suspend operator fun invoke() {
       val images = repository.getAllImagesFromDatabase()
       if(images.isNotEmpty()){
           repository.insertImages(images.map {it.toDatabase()})
       }
       else{
           repository.getAllImagesFromDatabase()
       }
   }
}