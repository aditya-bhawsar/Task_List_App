package com.aditya.to_do.di

import android.content.Context
import androidx.room.Room
import com.aditya.to_do.model.AppDatabase
import com.aditya.to_do.util.Utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context) = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java,
        DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNewsDao(database: AppDatabase) = database.getTaskDao()

}