package com.aditya.task_list.di

import android.content.Context
import androidx.room.Room
import com.aditya.task_list.data.AppDatabase
import com.aditya.task_list.util.Utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//Created With Application
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //Provides Room Database Instance to another provides method
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext ctx: Context) = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    //Provides News Dao to Repository
    @Singleton
    @Provides
    fun provideNewsDao(database: AppDatabase) = database.getTaskDao()

}