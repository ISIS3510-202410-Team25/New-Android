package com.example.foodu.model.service.module

import android.content.Context
import androidx.room.Query
import androidx.room.Room
import com.example.foodu.data.entity.ImageDatabase
import com.example.foodu.model.service.AccountService
import com.example.foodu.model.service.impl.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val IMAGE_DATABASE_NAME = "image_database"
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context,
        ImageDatabase::class.java, IMAGE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideImageDao(db:ImageDatabase) = db.getImageDao()
}